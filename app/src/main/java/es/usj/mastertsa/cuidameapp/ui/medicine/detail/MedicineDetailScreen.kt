package es.usj.mastertsa.cuidameapp.ui.medicine.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail
import es.usj.mastertsa.cuidameapp.domain.share.Util.Companion.calculateDays
import es.usj.mastertsa.cuidameapp.ui.indication.detail.DetailRow
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
    Column(modifier = Modifier.fillMaxSize().padding(26.dp)) {
        Spacer(modifier = Modifier.padding(vertical = 16.dp))
        Text(text = medication.name,
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
            color = MaterialTheme.colorScheme.primary
        )
        Text(text = "Vía de administración:  ${medication.administrationType}",
            color = Color.DarkGray,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Normal),
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Text("Descripcion:",
            modifier = Modifier.padding(bottom = 10.dp),
            style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold))
        Text(medication.description)
    }
}