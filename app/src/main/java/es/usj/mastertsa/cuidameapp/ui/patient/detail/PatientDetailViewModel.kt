package es.usj.mastertsa.cuidameapp.ui.patient.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.navigation.toRoute
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.IndicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.PatientRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.indication.GetIndicationByIdUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.GetIndicationsByPatientIdUseCase
import es.usj.mastertsa.cuidameapp.domain.patient.GetPatientByIdUseCase
import es.usj.mastertsa.cuidameapp.ui.navigation.MedicationDetail
import kotlinx.coroutines.launch

class PatientDetailViewModel(
    private val savedState: SavedStateHandle,
    private var patientUseCase: GetPatientByIdUseCase,
    private var indicationUseCase: GetIndicationsByPatientIdUseCase,
): ViewModel() {
    var uiState by mutableStateOf(PatientDetailUiState())
    private set

    val id = savedState.toRoute<MedicationDetail>().id

    suspend fun getPatientById(id: Long){
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
        try {
            val patient = patientUseCase.execute(id)
            uiState = uiState.copy(loading = false, patient = patient)
        }
        catch (ex: Exception){
            uiState = uiState.copy(loading = false, error = ex.message)
        }
        }
    }

    suspend fun getListOfIndicationsById(id: Long){
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
        try {
            val indications = indicationUseCase.execute(id)
            uiState = uiState.copy(loading = false, indicationList = indications)
        }
        catch (ex: Exception){
            uiState = uiState.copy(loading = false, error = ex.message)
        }
        }
    }
    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val patientRepo = PatientRepositoryImpl(PatientDatabase.provideDatabase(context))
                val indicationRepo = IndicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val savedStateHandle = extras.createSavedStateHandle()
                val patientUseCase = GetPatientByIdUseCase(patientRepo)
                val indicationUseCase = GetIndicationsByPatientIdUseCase(indicationRepo)

                return PatientDetailViewModel(savedStateHandle,patientUseCase,indicationUseCase) as T
            }
        }
    }
}