package es.usj.mastertsa.cuidameapp.ui.patient.detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.ui.shared.DetailTopBar

@Composable
fun PatientDetailScreen(
    viewModel: PatientDetailViewModel = viewModel(factory = PatientDetailViewModel.factory(
        LocalContext.current)),
    navigateBack: () -> Unit
) {
    val uiState = viewModel.uiState

    LaunchedEffect(viewModel.id) {
        viewModel.getPatientById(viewModel.id)
        viewModel.getListOfIndicationsById(viewModel.id)
    }

    Scaffold(
        topBar = { DetailTopBar("Detalles del Paciente", navigateBack) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                uiState.loading -> {
                    CircularProgressIndicator()
                }
                uiState.error != null -> {
                    Text(text = "Error: ${uiState.error}", color = MaterialTheme.colorScheme.error)
                }
                else -> {
                    uiState.patient?.let { patient ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {
                            // Tarjeta con información del paciente
                            ElevatedCard(
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(
                                    modifier = Modifier.padding(16.dp),
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        text = "${patient.firstName} ${patient.lastName}",
                                        style = MaterialTheme.typography.headlineSmall
                                    )
                                    Divider()
                                    PatientDetailItem(Icons.Default.AccountCircle, "${patient.identificationType}: ${patient.identification}")
                                    PatientDetailItem(Icons.Default.Call, "Contacto: ${patient.emergencyContact}")
                                    PatientDetailItem(Icons.Default.Info, "Nacimiento: ${patient.birthDate}")
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            // Lista de indicaciones médicas
                            if (uiState.indicationList.isNotEmpty()) {
                                Text(
                                    text = "Indicaciones Médicas",
                                    style = MaterialTheme.typography.titleMedium,
                                    modifier = Modifier.padding(vertical = 8.dp)
                                )
                                uiState.indicationList.forEach { indication ->
                                    ElevatedCard(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 4.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
                                    ) {
                                        Column(
                                            modifier = Modifier.padding(16.dp),
                                            verticalArrangement = Arrangement.spacedBy(4.dp)
                                        ) {
                                            Text(
                                                text = indication.medicineName,
                                                style = MaterialTheme.typography.titleSmall,
                                                color = MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                            PatientDetailItem(Icons.Default.Info, "ID Medicamento: ${indication.medicineId}")
                                            PatientDetailItem(Icons.Default.DateRange, "Recurrencia: ${indication.recurrenceId}")
                                            PatientDetailItem(Icons.Default.ArrowForward, "Inicio: ${indication.startDate}")
                                            PatientDetailItem(Icons.Default.ArrowBack, "Dosis: ${indication.dosage}")
                                        }
                                    }
                                }
                            } else {
                                Text(
                                    text = "No hay indicaciones registradas.",
                                    modifier = Modifier.padding(top = 8.dp),
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PatientDetailItem(icon: androidx.compose.ui.graphics.vector.ImageVector, text: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(imageVector = icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.width(8.dp))
        Text(text, style = MaterialTheme.typography.bodyMedium)
    }
}
