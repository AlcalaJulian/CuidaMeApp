package es.usj.mastertsa.cuidameapp.ui.medicine.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail
import es.usj.mastertsa.cuidameapp.ui.shared.DetailTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator

@Composable
fun MedicineDetailScreen(
    viewModel: MedicineDetailViewModel = viewModel(factory = MedicineDetailViewModel.factory(
        LocalContext.current)),
    navigateBack:()-> Unit
){
    val uiState = viewModel.uiState

    Scaffold(
        topBar = { DetailTopBar("Detalle de la medicación", navigateBack) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                uiState.loading -> {
                    LoadingIndicator()
                }
                uiState.error != null -> {
                    ErrorText(message = uiState.error)
                }
                else -> {
                    uiState.data?.let { MedicineDetailContent(it) }
                }
            }
        }
    }
}

@Composable
fun MedicineDetailContent(medication: MedicineDetail) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {

        Text(text = "Name: ${medication.name}", style = androidx.compose.material3.MaterialTheme.typography.bodyLarge)
        Text(text = "Description: ${medication.description}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        Text(text = "Administration Type: ${medication.administrationType}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)

        Spacer(modifier = Modifier.height(16.dp))
    }
}