package es.usj.mastertsa.cuidameapp.ui.indication.detail

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
import es.usj.mastertsa.cuidameapp.domain.indication.GetIndicationByIdUseCase
import es.usj.mastertsa.cuidameapp.ui.navigation.MedicationDetail
import kotlinx.coroutines.launch

class IndicationDetailViewModel(
    private val useCase: GetIndicationByIdUseCase,
    saveHandle: SavedStateHandle
): ViewModel() {
    var uiState by mutableStateOf(IndicationDetailUiState())
        private set

    val id = saveHandle.toRoute<MedicationDetail>().id

    init {
        getIndicationById(id)
    }

    private fun getIndicationById(id: Long){
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
            try {
                val indications = useCase.execute(id)
                uiState = uiState.copy(loading = false, data = indications)
            }
            catch (ex: Exception){
                uiState = uiState.copy(loading = false, error = ex.message)
            }
        }
    }
    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val repo = IndicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = GetIndicationByIdUseCase(repo)
                val saveHandle = extras.createSavedStateHandle()
                return IndicationDetailViewModel(useCase, saveHandle) as T
            }
        }
    }
}