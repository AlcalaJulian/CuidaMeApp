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
import es.usj.mastertsa.cuidameapp.data.repository.MedicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.PatientRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.indication.AddAnIndicationToPatientUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.DeleteIndicationUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.GetAllIndicationsUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.medication.GetAllMedicationsUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetAllPatientsUseCase
import kotlinx.coroutines.launch

class IndicationListViewModel(
    private val getAllIndicationsUseCase: GetAllIndicationsUseCase,
    private val addAnIndicationToPatientUseCase: AddAnIndicationToPatientUseCase,
    private val getAllPatientsUseCase: GetAllPatientsUseCase,
    private val getAllMedicationsUseCase: GetAllMedicationsUseCase,
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
    fun addPatient(indication: Indication) {
        indications = indications.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            try {
                addAnIndicationToPatientUseCase.execute(indication = indication)
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

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val indicationRepositoryImpl = IndicationRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val patientRepositoryImpl = PatientRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val medicationsRepositoryImpl = MedicationRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val getAllIndicationsUseCase = GetAllIndicationsUseCase(indicationRepositoryImpl)
                val addAnIndicationToPatientUseCase = AddAnIndicationToPatientUseCase(indicationRepositoryImpl)
                val getAllPatientsUseCase = GetAllPatientsUseCase(patientRepositoryImpl)
                val getAllMedicationsUseCase = GetAllMedicationsUseCase(medicationsRepositoryImpl)
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