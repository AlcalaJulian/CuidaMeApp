package es.usj.mastertsa.cuidameapp.ui.indication.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import es.usj.mastertsa.cuidameapp.domain.indication.GetAllIndicationsUseCase

class IndicationListViewModel(
    private val getListUseCase: GetAllIndicationsUseCase
): ViewModel() {


    companion object{
        fun factory(): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {


                return super.create(modelClass)
            }
        }
    }
}