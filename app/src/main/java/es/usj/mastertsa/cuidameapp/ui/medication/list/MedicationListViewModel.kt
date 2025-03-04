package es.usj.mastertsa.cuidameapp.ui.medication.list

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.MedicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.medication.GetAllMedicationsUseCase

class MedicationListViewModel(
    private val useCase: GetAllMedicationsUseCase
): ViewModel() {

    var uiState by mutableStateOf(MedicationListUiState())
        private set

init {

}
     suspend fun getAllMedications(){
        uiState = uiState.copy(loading = true)
        try {
            val medicaments = useCase.execute()
            uiState = uiState.copy(loading = false, data = medicaments)
        }
        catch (ex: Exception){
            uiState = uiState.copy(loading = false, error = ex.message)
        }
    }

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {

                val repo = MedicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = GetAllMedicationsUseCase(repo)
                return MedicationListViewModel(useCase) as T
            }
        }
    }
}