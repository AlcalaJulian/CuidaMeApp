package es.usj.mastertsa.cuidameapp.ui.medication.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MedicationDetailViewModel: ViewModel() {

    companion object{
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                return super.create(modelClass)
            }
        }
    }
}