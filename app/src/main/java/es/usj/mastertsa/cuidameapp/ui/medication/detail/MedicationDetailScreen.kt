package es.usj.mastertsa.cuidameapp.ui.medication.detail

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MedicationDetailScreen(
    viewModel: MedicationDetailViewModel = viewModel(factory = MedicationDetailViewModel.factory(
        LocalContext.current)),
    navigateBack:()-> Unit
){

}