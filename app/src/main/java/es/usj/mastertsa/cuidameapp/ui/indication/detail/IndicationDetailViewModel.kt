package es.usj.mastertsa.cuidameapp.ui.indication.detail

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.repository.IndicationRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.indication.GetIndicationByIdUseCase

class IndicationDetailViewModel(
    private val useCase: GetIndicationByIdUseCase,
    saveHandle: SavedStateHandle
) {

    companion object{
        fun factory(context: Context): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {

                val repo = IndicationRepositoryImpl(PatientDatabase.provideDatabase(context))
                val useCase = GetIndicationByIdUseCase(repo)
                val saveHandle = extras.createSavedStateHandle()
                return IndicationDetailViewModel(useCase, saveHandle) as T
            }
        }
    }
}