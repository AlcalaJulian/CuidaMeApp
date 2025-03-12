package es.usj.mastertsa.cuidameapp.ui.medicine.detail

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
import es.usj.mastertsa.cuidameapp.data.repository.MedicineRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.medicine.GetMedicineByIdUseCase
import es.usj.mastertsa.cuidameapp.ui.navigation.MedicineDetail
import kotlinx.coroutines.launch

class MedicineDetailViewModel(
    savedState: SavedStateHandle,
    private val useCase: GetMedicineByIdUseCase
): ViewModel() {
var uiState by mutableStateOf(MedicineDetailUiState())
    private set

    val id = savedState.toRoute<MedicineDetail>().id

    init {
        getMedicamentById(id)
    }

    private fun getMedicamentById(id: Long){
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
        try {
            val medicament = useCase.execute(id)
            uiState = uiState.copy(loading = false, data = medicament)
        }
        catch (ex: Exception){
            uiState = uiState.copy(loading = false, error = ex.message)
        }
            }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val repo = MedicineRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = GetMedicineByIdUseCase(repo)
                val savedStateHandle = extras.createSavedStateHandle()
                return MedicineDetailViewModel(savedStateHandle, useCase) as T
            }
        }
    }
}