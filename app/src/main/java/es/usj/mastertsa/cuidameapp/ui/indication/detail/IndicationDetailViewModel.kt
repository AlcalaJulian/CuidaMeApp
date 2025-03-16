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
import es.usj.mastertsa.cuidameapp.data.repository.RecurrenceRepositoryIml
import es.usj.mastertsa.cuidameapp.domain.indication.DeleteDosageUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.DeleteIndicationUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.GetDosagesForIndicationUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.GetIndicationByIdUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.UpdateDosageCompletionUseCase
import es.usj.mastertsa.cuidameapp.domain.indication.UpdateIndicationUseCase
import es.usj.mastertsa.cuidameapp.ui.navigation.MedicineDetail
import kotlinx.coroutines.launch

class IndicationDetailViewModel(
    private val useCase: GetIndicationByIdUseCase,
    private val updateDosageCompletionUseCase: UpdateDosageCompletionUseCase,
    private val getDosagesForIndicationUseCase: GetDosagesForIndicationUseCase,
    private val deleteIndicationUseCase: DeleteIndicationUseCase,
    private val updateIndicationUseCase: UpdateIndicationUseCase,
    private val deleteDosageUseCase: DeleteDosageUseCase,
    saveHandle: SavedStateHandle
): ViewModel() {
    var uiState by mutableStateOf(IndicationDetailUiState())
        private set

    val id = saveHandle.toRoute<MedicineDetail>().id

    init {
        getIndicationById(id)
        getDosages(id)
    }

     private fun getIndicationById(id: Long){

        viewModelScope.launch {
            uiState = uiState.copy(loading = true)
            try {
                val indications = useCase.execute(id)
                uiState = uiState.copy(loading = false, data = indications)
            }
            catch (ex: Exception){
                uiState = uiState.copy(loading = false, error = ex.message)
            }
        }
    }

    fun updateIndication(indication: Indication) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                updateIndicationUseCase.execute(indication)
                //uiState = uiState.copy(loading = false, data = getIndicationById(id))  // Set updated data
            } catch (e: Exception) {
                uiState = uiState.copy(loading = false, error = "Failed to update indication")  // Set error state
            }
        }
    }

    fun deleteIndication(indicationId: Long) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                deleteIndicationUseCase.execute(indicationId)
                uiState = uiState.copy(data = null)  // Set data to null after deletion
            } catch (e: Exception) {
                uiState = uiState.copy(loading = false, error = "Failed to delete indication")  // Set error state
            }
        }
    }

    // Fetch the dosages for a given indication
    private fun getDosages(indicationId: Long) {
        viewModelScope.launch {
            try {
                val dosages = getDosagesForIndicationUseCase.execute(indicationId)
                uiState = uiState.copy(loading = false, dosages = dosages)
            } catch (e: Exception) {
                uiState = uiState.copy(loading = false, error = "Error al cosultar las dosis")
            }
        }
    }

    fun markDosageAsComplete(dosageId: Long) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                updateDosageCompletionUseCase.execute(dosageId, true)
                val updatedDosages = uiState.dosages.map {
                    if (it.id == dosageId) it.copy(completed = true) else it
                }
                uiState = uiState.copy(loading = false, dosages = updatedDosages)
            } catch (e: Exception) {
                uiState = uiState.copy(loading = false, error = "Error al completar la dosis")
            }
        }
    }

    fun deleteDosage(dosageId: Long) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(loading = true)
                deleteDosageUseCase.execute(dosageId)
                val updatedDosages = uiState.dosages.filter { it.id != dosageId }
                uiState = uiState.copy(loading = false, dosages = updatedDosages)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val indicationRepository = IndicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val recurrenceRepository = RecurrenceRepositoryIml(PatientDatabase.provideDatabase(context))
                val useCase = GetIndicationByIdUseCase(indicationRepository)
                val updateDosageCompletionUseCase = UpdateDosageCompletionUseCase(recurrenceRepository)
                val getDosagesForIndicationUseCase = GetDosagesForIndicationUseCase(recurrenceRepository)
                val deleteIndicationUseCase = DeleteIndicationUseCase(indicationRepository)
                val updateIndicationUseCase = UpdateIndicationUseCase(indicationRepository)
                val deleteDosageUseCase = DeleteDosageUseCase(recurrenceRepository)


                val saveHandle = extras.createSavedStateHandle()
                return IndicationDetailViewModel(
                    useCase,
                    updateDosageCompletionUseCase,
                    getDosagesForIndicationUseCase,
                    deleteIndicationUseCase,
                    updateIndicationUseCase,
                    deleteDosageUseCase,
                    saveHandle) as T
            }
        }
    }
}