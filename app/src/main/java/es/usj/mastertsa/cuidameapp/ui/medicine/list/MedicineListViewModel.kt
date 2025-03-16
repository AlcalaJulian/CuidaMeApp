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
    private val getAllMedicationsUseCase: GetAllMedicinesUseCase,
    private val deleteMedicationUseCase: DeleteMedicineUseCase,
    private val repository: MedicineRepositoryImpl
) : ViewModel() {

    var uiState by mutableStateOf(MedicineListUiState())
        private set

    init {
        syncMedicationsFromFirestore()
        getAllMedications()
    }

    fun getAllMedications() {
        uiState = uiState.copy(loading = true)
        viewModelScope.launch {
            try {
                val medications = getAllMedicationsUseCase.execute()
                uiState = uiState.copy(loading = false, data = medications)
            } catch (exception: Exception) {
                uiState = uiState.copy(loading = false, error = exception.message)
            }
        }
    }

    fun deleteMedication(id: Long) {
        viewModelScope.launch {
            try {
                deleteMedicationUseCase.execute(id)
                getAllMedications()
            } catch (exception: Exception) {
                uiState = uiState.copy(error = exception.message)
            }
        }
    }

    fun syncMedicationsFromFirestore() {
        repository.syncMedicationsFromFirestore()
    }

    companion object {
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val database = PatientDatabase.provideDatabase(context)
                val repository = MedicineRepositoryImpl(database)
                val getAllMedicationsUseCase = GetAllMedicinesUseCase(repository)
                val deleteMedicationUseCase = DeleteMedicineUseCase(repository)

                return MedicineListViewModel(
                    getAllMedicationsUseCase,
                    deleteMedicationUseCase,
                    repository
                ) as T
            }
        }
    }
}
