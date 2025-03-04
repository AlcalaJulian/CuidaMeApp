package es.usj.mastertsa.cuidameapp.ui.medication.detail

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.medication.GetMedicationByIdUseCase
import es.usj.mastertsa.cuidameapp.ui.navigation.MedicationDetail
import kotlinx.coroutines.launch

class MedicationDetailViewModel(
    private val savedState: SavedStateHandle,
    private val useCase: GetMedicationByIdUseCase
): ViewModel() {
var uiState by mutableStateOf(MedicationDetailUiState())
    private set

    val id = savedState.toRoute<MedicationDetail>().id

    init {
        viewModelScope.launch {
            getMedicamentById(id)
        }
    }

    private suspend fun getMedicamentById(id: Long){
        uiState = uiState.copy(loading = true)
        try {
            val medicament = useCase.execute(id)
            uiState = uiState.copy(loading = false, data = medicament)
        }
        catch (ex: Exception){
            uiState = uiState.copy(loading = false, error = ex.message)
        }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val repo = MedicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = GetMedicationByIdUseCase(repo)
                return MedicationDetailViewModel(useCase)
            }
        }
    }
}