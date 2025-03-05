package es.usj.mastertsa.cuidameapp.ui.medication.add

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.MedicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.medication.AddMedicationUseCase
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.ui.medication.list.MedicationListViewModel
import kotlinx.coroutines.launch

class MedicationAddViewModel(private val useCase: AddMedicationUseCase): ViewModel() {

    var uiState by mutableStateOf(MedicationAddUiState())
        private set

     fun addMedication(medication: Medication){
        viewModelScope.launch {
            try {
                useCase.execute(medication)
            }catch (ex: Exception){
                uiState = uiState.copy(loading = false, error = ex.message)
            }
        }


    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val repo = MedicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = AddMedicationUseCase(repo)
                return MedicationAddViewModel(useCase) as T
            }
        }
    }
}