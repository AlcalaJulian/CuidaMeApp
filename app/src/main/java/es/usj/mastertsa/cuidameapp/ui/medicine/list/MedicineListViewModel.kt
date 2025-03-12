package es.usj.mastertsa.cuidameapp.ui.medicine.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.MedicineRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.medicine.DeleteMedicineUseCase
import es.usj.mastertsa.cuidameapp.domain.medicine.GetAllMedicinesUseCase
import kotlinx.coroutines.launch

class MedicineListViewModel(
    private val useCase: GetAllMedicinesUseCase,
    private val deleteUseCase: DeleteMedicineUseCase
): ViewModel() {

    var uiState by mutableStateOf(MedicineListUiState())
        private set


    fun getAllMedications(){
        uiState = uiState.copy(loading = true)
         viewModelScope.launch {
             try {
                 val medicaments = useCase.execute()
                 uiState = uiState.copy(loading = false, data = medicaments)
             } catch (ex: Exception) {
                 uiState = uiState.copy(loading = false, error = ex.message)
             }
         }
    }

    fun deleteMedication(id: Long) {
        viewModelScope.launch {
            try {
                deleteUseCase.execute(id)
                getAllMedications()  // Refresh the list after deletion
            } catch (e: Exception) {
                uiState = uiState.copy(error = "Failed to delete medication")
            }
        }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val repo = MedicineRepositoryImpl(PatientDatabase.provideDatabase(context))
                val getAllUseCase = GetAllMedicinesUseCase(repo)
                val deleteUseCase = DeleteMedicineUseCase(repo)
                return MedicineListViewModel(getAllUseCase, deleteUseCase) as T
            }
        }

    }
}