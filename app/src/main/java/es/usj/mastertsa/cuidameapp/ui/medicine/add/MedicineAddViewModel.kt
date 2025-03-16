package es.usj.mastertsa.cuidameapp.ui.medicine.add

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.MedicineRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.medicine.AddMedicineUseCase
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import kotlinx.coroutines.launch

class MedicineAddViewModel(private val useCase: AddMedicineUseCase): ViewModel() {

    var uiState by mutableStateOf(MedicineAddUiState())
        private set

     fun addMedication(medication: Medicine){
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

                val repo = MedicineRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = AddMedicineUseCase(repo)
                return MedicineAddViewModel(useCase) as T
            }
        }
    }
}