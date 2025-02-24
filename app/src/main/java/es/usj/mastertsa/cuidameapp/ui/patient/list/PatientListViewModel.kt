package es.usj.mastertsa.cuidameapp.ui.patient.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class PatientListViewModel: ViewModel() {

    companion object{
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                return super.create(modelClass)
            }
        }
    }
}