package es.usj.mastertsa.cuidameapp.ui.patient.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.PatientRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.patient.AddPatientUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.DeletePatientUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetAllPatientsUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import kotlinx.coroutines.launch

class PatientListViewModel(
    private val getAllPatient:GetAllPatientsUseCase,
    private val addPatient: AddPatientUseCase,
    private val deletePatien: DeletePatientUseCase
): ViewModel() {
    var patients by mutableStateOf(PatientListUiState())
    private set


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

    fun getAllPatients(){
        patients = patients.copy(loading = true)
        viewModelScope.launch {
            try {
                val useCaseGetAllPatients = getAllPatient.execute()
                patients = patients.copy(data = useCaseGetAllPatients, loading = false)
            } catch(exception:Exception) {
                patients = patients.copy(error = exception.message, loading = false)
            }

        }
    }

    fun deleteMedication(id: Long) {
        viewModelScope.launch {
            try {
                deletePatien.execute(id)
                patients = patients.copy(loading = false)
            } catch(exception:Exception) {
                patients = patients.copy(error = exception.message, loading = false)
            }

        }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>):  T {

                val patientRepositoryImpl = PatientRepositoryImpl(
                    db = PatientDatabase.provideDatabase(context)
                )
                val getAllPatients = GetAllPatientsUseCase(patientRepositoryImpl)
                val addPatientUseCase = AddPatientUseCase(patientRepositoryImpl)
                val deletePatientUseCase = DeletePatientUseCase(patientRepositoryImpl)

                return PatientListViewModel(getAllPatients,addPatientUseCase, deletePatientUseCase) as T
            }
        }
    }
}