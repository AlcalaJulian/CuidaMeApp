package es.usj.mastertsa.cuidameapp.ui.indication.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.IndicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.MedicineRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.PatientRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.RecurrenceRepositoryIml
import es.usj.mastertsa.cuidameapp.domain.indication.AddAnIndicationToPatientUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.DeleteIndicationUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage
import es.usj.mastertsa.cuidameapp.domain.indication.GetAllIndicationsUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence
import es.usj.mastertsa.cuidameapp.domain.indication.SyncIndicationsUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.SyncRecurrenceUseCase
import es.usj.mastertsa.cuidameapp.domain.medicine.GetAllMedicinesUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetAllPatientsUseCase
import kotlinx.coroutines.launch
import java.util.Locale

class IndicationListViewModel(
    private val getAllIndicationsUseCase: GetAllIndicationsUseCase,
    private val addAnIndicationToPatientUseCase: AddAnIndicationToPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val getAllMedicationsUseCase: GetAllMedicinesUseCase,
    private val deleteIndicationUseCase: DeleteIndicationUseCase,
    private val syncIndicationsUseCase: SyncIndicationsUseCase,
    private val syncRecurrenceUseCase: SyncRecurrenceUseCase
): ViewModel() {
    var indicationUiState by mutableStateOf(IndicationListUiState())
        private set

    fun getAllMedications(){
        viewModelScope.launch {
            try {
//                syncIndicationsUseCase.execute()
//                syncRecurrenceUseCase.execute()
                val useCaseGetAllMedications = getAllMedicationsUseCase.execute()
                indicationUiState = indicationUiState.copy(medicationsList = useCaseGetAllMedications, loading = false)
            } catch(exception:Exception) {
                indicationUiState = indicationUiState.copy(error = exception.message, loading = false)
            }

        }
    }

    fun getAllPatients(){
        viewModelScope.launch {
            try {
                val useCaseGetAllPatients = getAllPatientsUseCase.execute()
                indicationUiState = indicationUiState.copy(patientList = useCaseGetAllPatients, loading = false)
            } catch(exception:Exception) {
                indicationUiState = indicationUiState.copy(error = exception.message, loading = false)
            }

        }

    }

    fun getAllIndications(){
        indicationUiState = indicationUiState.copy(loading = true)
        viewModelScope.launch {
            try {
                val useCaseGetAllIndications = getAllIndicationsUseCase.execute()
                indicationUiState = indicationUiState.copy(data = useCaseGetAllIndications, loading = false)
            } catch(exception:Exception) {
                indicationUiState = indicationUiState.copy(error = exception.message, loading = false)
            }

        }
    }

    fun deleteIndication(id: Long) {
        viewModelScope.launch {
            try {
                deleteIndicationUseCase.execute(id)
                indicationUiState = indicationUiState.copy(loading = false, data = indicationUiState.data.filterNot { it.id == id })
            } catch (e: Exception) {
                indicationUiState = indicationUiState.copy(error = "Failed to delete indication")
            }
        }
    }

    // Add indication and its recurrences
    fun addIndicationAndRecurrences(indication: Indication, dosis: List<Dosage>) {
        viewModelScope.launch {
            try {
                indicationUiState = indicationUiState.copy(loading = true)
                // Generate recurrences based on the recurrence type
                val recurrences = generateRecurrences(indication, dosis)
                addAnIndicationToPatientUseCase.execute(indication, recurrences)
                indicationUiState = indicationUiState.copy(loading = false)
            } catch (e: Exception) {
                indicationUiState = indicationUiState.copy(error = "Error al guardar la indicación.", loading = false)
            }

        }
    }

    // Generate recurrences based on indication's recurrence pattern
    private fun generateRecurrences(indication: Indication, dosages: List<Dosage>): List<Recurrence> {
        val recurrences = mutableListOf<Recurrence>()
        val startDate = indication.startDate
        val dosage = indication.dosage
        val recurrencePattern =
            indication.recurrenceId.lowercase(Locale.getDefault()) // e.g., "every 4 hours", "every day", "weekly"

        // Logic for calculating recurrences
        when {
            recurrencePattern.contains("cada") -> {
                //val interval = recurrencePattern.split(" ")[1].toIntOrNull() ?: 1
                val recurrenceType = recurrencePattern.split(" ")[1]

                var currentDate = startDate

                for (i in 1..indication.dosage) {

                    dosages.forEach {
                        val recurrence = Recurrence(
                            indicationId = indication.id,
                            specificDate = currentDate,
                            completed = false,
                            id = 0L,
                            hour = it.hour,
                            quantity = it.quantity.toInt()
                        )
                        recurrences.add(recurrence)
                    }

                    // Calculate next recurrence based on the recurrence type
                    when (recurrenceType) {
                        "día" -> {
                            currentDate = incrementDateByDays(currentDate, 1)
                        }
//                        "hora" -> {
//                            currentHour = incrementHourBy(currentHour, interval)
//                        }
                        "semana" -> {
                            currentDate = incrementDateByWeeks(currentDate, 1)
                        }
                    }
                }
            }
        }
        return recurrences
    }

    // Function to increment date by days (simple logic for this example)
    private fun incrementDateByDays(date: String, days: Int): String {
        val dateParts = date.split("-")
        val year = dateParts[2].toInt()
        val month = dateParts[1].toInt()
        val day = dateParts[0].toInt() + days

        return "${day.toString().padStart(2, '0')}-${month.toString().padStart(2, '0')}-$year"
    }

    // Function to increment time by hours (simple placeholder for time manipulation)
    private fun incrementHourBy(time: String, hoursToAdd: Int): String {
        val timeParts = time.split(":")
        val hour = timeParts[0].toInt()
        val newHour = (hour + hoursToAdd) % 24
        return "$newHour:${timeParts[1]}"
    }

    // Function to increment date by weeks
    private fun incrementDateByWeeks(date: String, weeks: Int): String {
        return incrementDateByDays(date, weeks * 7)
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val indicationRepositoryImpl = IndicationRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val patientRepositoryImpl = PatientRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val medicationsRepositoryImpl = MedicineRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )

                val recurrenceRepository = RecurrenceRepositoryIml(PatientDatabase.provideDatabase(context))
                val getAllIndicationsUseCase = GetAllIndicationsUseCase(indicationRepositoryImpl)

                val addAnIndicationToPatientUseCase = AddAnIndicationToPatientUseCase(indicationRepositoryImpl, recurrenceRepository)
                val getAllPatientsUseCase = GetAllPatientsUseCase(patientRepositoryImpl)
                val getAllMedicationsUseCase = GetAllMedicinesUseCase(medicationsRepositoryImpl)
                val deleteUseCase = DeleteIndicationUseCase(indicationRepositoryImpl)
                var syncRecurrenceUseCase = SyncRecurrenceUseCase(recurrenceRepository)
                var syncIndicationUseCase = SyncIndicationsUseCase(indicationRepositoryImpl)

                return IndicationListViewModel(
                    getAllIndicationsUseCase,
                    addAnIndicationToPatientUseCase,
                    getAllPatientsUseCase,
                    getAllMedicationsUseCase,
                    deleteUseCase,
                    syncIndicationUseCase,
                    syncRecurrenceUseCase
                ) as T
            }
        }
    }

}