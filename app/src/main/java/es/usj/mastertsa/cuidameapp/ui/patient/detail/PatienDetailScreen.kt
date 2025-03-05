package es.usj.mastertsa.cuidameapp.ui.patient.detail

import androidx.compose.runtime.Composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController

@Composable
fun PatientDetailScreen(
    viewModel: PatientDetailViewModel = viewModel(factory = PatientDetailViewModel.factory(
        LocalContext.current)),
    navigateBack:()-> Unit
) {
    val uiState = viewModel.uiState
    LaunchedEffect(key1 = viewModel.id) {
        viewModel.getPatientById(viewModel.id)
        viewModel.getListOfIndicationsById(viewModel.id)
    }

    when {
        uiState.loading -> {
            // Muestra un indicador de carga
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        uiState.error != null -> {
            // Muestra un mensaje de error si algo falla
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error: ${uiState.error}")
            }
        }
        else -> {
            // Muestra la información del paciente y la lista de indicaciones
            uiState.patient?.let { patient ->
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nombre: ${patient.firstName} ${patient.lastName}")
                    Text(text = "Identificación: ${patient.identification}")
                    Text(text = "Contacto de emergencia: ${patient.emergencyContact}")
                    Text(text = "Fecha de nacimiento: ${patient.birthDate}")

                    if (uiState.indicationList.isNotEmpty()) {
                        Text(text = "Indicaciones:")
                        uiState.indicationList.forEach { indication ->
                            Text("• ID: ${indication.id}, " +
                                    "MedicamentoID: ${indication.medicationId}, " +
                                    "Recurrencia: ${indication.recurrenceId}, " +
                                    "Fecha inicio: ${indication.startDate}, " +
                                    "Dosis: ${indication.dosage}")
                        }
                    } else {
                        Text(text = "No hay indicaciones registradas.")
                    }
                }
            }
        }
    }
}
