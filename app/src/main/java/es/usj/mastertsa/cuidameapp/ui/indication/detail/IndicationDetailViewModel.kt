package es.usj.mastertsa.cuidameapp.ui.indication.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class IndicationDetailViewModel: ViewModel() {

    companion object{
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                return super.create(modelClass)
            }
        }
    }
}