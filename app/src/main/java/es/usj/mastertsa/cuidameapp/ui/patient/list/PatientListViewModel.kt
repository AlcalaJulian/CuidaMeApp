package es.usj.mastertsa.cuidameapp.ui.patient.list

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.PatientRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.patient.AddPatientUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.DeletePatientUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetAllPatientsUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import kotlinx.coroutines.launch

class PatientListViewModel(
    private val getAllPatient: GetAllPatientsUseCase,
    private val addPatient: AddPatientUseCase,
    private val deletePatient: DeletePatientUseCase,
    private val repository: PatientRepositoryImpl
) : ViewModel() {

    var patients by mutableStateOf(PatientListUiState())
        private set

    init {
        syncPatientsFromFirestore()
        getAllPatients()
    }

    fun addPatient(patient: Patient) {
        patients = patients.copy(loading = true, error = null, success = false)
        viewModelScope.launch {
            try {
                addPatient.execute(patient)
                patients = patients.copy(loading = false, success = true)
                getAllPatients()
            } catch (exception: Exception) {
                patients = patients.copy(loading = false, error = exception.message)
            }
        }
    }


    fun getAllPatients() {
        patients = patients.copy(loading = true)
        viewModelScope.launch {
            try {
                val useCaseGetAllPatients = getAllPatient.execute()
                patients = patients.copy(data = useCaseGetAllPatients, loading = false)
            } catch (exception: Exception) {
                patients = patients.copy(error = exception.message, loading = false)
            }
        }
    }

    fun deletePatient(id: Long) {
        patients = patients.copy(loading = true)
        viewModelScope.launch {
            try {
                deletePatient.execute(id)
                patients = patients.copy(loading = false)
                getAllPatients()
            } catch (exception: Exception) {
                patients = patients.copy(error = exception.message, loading = false)
            }
        }
    }

    fun syncPatientsFromFirestore() {
        repository.syncPatientsFromFirestore()
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = PatientDatabase.provideDatabase(context)
                val repository = PatientRepositoryImpl(database)
                val getAllPatients = GetAllPatientsUseCase(repository)
                val addPatientUseCase = AddPatientUseCase(repository)
                val deletePatientUseCase = DeletePatientUseCase(repository)

                return PatientListViewModel(
                    getAllPatients,
                    addPatientUseCase,
                    deletePatientUseCase,
                    repository
                ) as T
            }
        }
    }
}
