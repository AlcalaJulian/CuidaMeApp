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
import es.usj.mastertsa.cuidameapp.domain.medicine.GetAllMedicinesUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetAllPatientsUseCase
import kotlinx.coroutines.launch

class IndicationListViewModel(
    private val getAllIndicationsUseCase: GetAllIndicationsUseCase,
    private val addAnIndicationToPatientUseCase: AddAnIndicationToPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val getAllMedicationsUseCase: GetAllMedicinesUseCase,
    private val deleteIndicationUseCase: DeleteIndicationUseCase
): ViewModel() {
    var indications by mutableStateOf(IndicationListUiState())
        private set

    fun getAllMedications(){
        viewModelScope.launch {
            try {
                val useCaseGetAllMedications = getAllMedicationsUseCase.execute()
                indications = indications.copy(medicationsList = useCaseGetAllMedications, loading = false)
            } catch(exception:Exception) {
                indications = indications.copy(error = exception.message, loading = false)
            }

        }
    }

    fun getAllPatients(){
        viewModelScope.launch {
            try {
                val useCaseGetAllPatients = getAllPatientsUseCase.execute()
                indications = indications.copy(patientList = useCaseGetAllPatients, loading = false)
            } catch(exception:Exception) {
                indications = indications.copy(error = exception.message, loading = false)
            }

        }

    }
    fun addPatient(indication: Indication, ) {
        indications = indications.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            try {
                addAnIndicationToPatientUseCase.execute(indication, emptyList())
                indications = indications.copy(loading = false, success = true)
                getAllIndications()
            } catch (exception: Exception) {
                indications = indications.copy(loading = false, error = exception.message)
            }
        }
    }

    fun getAllIndications(){
        indications = indications.copy(loading = true)
        viewModelScope.launch {
            try {
                val useCaseGetAllIndications = getAllIndicationsUseCase.execute()
                indications = indications.copy(data = useCaseGetAllIndications, loading = false)
            } catch(exception:Exception) {
                indications = indications.copy(error = exception.message, loading = false)
            }

        }
    }

    fun deleteIndication(id: Long) {
        viewModelScope.launch {
            try {
                deleteIndicationUseCase.execute(id)
            } catch (e: Exception) {
                indications = indications.copy(error = "Failed to delete indication")
            }
        }
    }

    // Add indication and its recurrences
    fun addIndicationAndRecurrences(indication: Indication, dosis: List<Dosage>) {
        viewModelScope.launch {
            // Generate recurrences based on the recurrence type
            val recurrences = generateRecurrences(indication, dosis)
            addAnIndicationToPatientUseCase.execute(indication, recurrences)
        }
    }

    // Generate recurrences based on indication's recurrence pattern
    private fun generateRecurrences(indication: Indication, dosages: List<Dosage>): List<Recurrence> {
        val recurrences = mutableListOf<Recurrence>()
        val startDate = indication.startDate
        //val startHour = indication.hour
        val dosage = indication.dosage
        val recurrencePattern = indication.recurrenceId // e.g., "every 4 hours", "every day", "weekly"

        // Logic for calculating recurrences
        when {
            recurrencePattern.contains("every") -> {
                val interval = recurrencePattern.split(" ")[1].toIntOrNull() ?: 1
                val recurrenceType = recurrencePattern.split(" ")[2]

                // Generate recurrences based on the interval
                var currentDate = startDate
                //var currentHour = startHour

                // Loop to generate recurrences based on the interval and type
                for (i in 1..dosage)

                dosages.forEach{
                    val recurrence = Recurrence(
                        indicationId = indication.id,
                        specificDate = currentDate,
                        //hour = currentHour,
                        completed = false,
                        id = 0L,
                        hour = it.hour
                    )
                    recurrences.add(recurrence)

                    // Calculate next recurrence based on the recurrence type
                    when (recurrenceType) {
                        "day" -> {
                            currentDate = incrementDateByDays(currentDate, 1)
                        }
//                        "hour" -> {
//                            currentHour = incrementHourBy(currentHour, interval)
//                        }
                        "week" -> {
                            currentDate = incrementDateByWeeks(currentDate, 1)
                        }
                    }
                }
            }
            else -> {
                // Handle other recurrence patterns here if needed
            }
        }
        return recurrences
    }

    // Function to increment date by days (simple logic for this example)
    private fun incrementDateByDays(date: String, days: Int): String {
        // You can use a date manipulation library or implement your own date logic
        // This is just a simple placeholder for date increment
        val dateParts = date.split("-")
        val year = dateParts[0].toInt()
        val month = dateParts[1].toInt()
        val day = dateParts[2].toInt() + days

        // You should implement proper date validation, this is simplified
        return "$year-${month.toString().padStart(2, '0')}-${day.toString().padStart(2, '0')}"
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
        // Increment date by weeks, same logic as increment days
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
                return IndicationListViewModel(
                    getAllIndicationsUseCase,
                    addAnIndicationToPatientUseCase,
                    getAllPatientsUseCase,
                    getAllMedicationsUseCase,
                    deleteUseCase
                ) as T
            }
        }
    }

}