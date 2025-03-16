package es.usj.mastertsa.cuidameapp.ui.patient.list

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    navigateToDetail: (id: Long) -> Unit,
    viewModel: PatientListViewModel = viewModel(
        factory = PatientListViewModel.factory(LocalContext.current)
    )
) {
    val uiState = viewModel.patients
    var showDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.syncPatientsFromFirestore()
        viewModel.getAllPatients()
    }

    Scaffold(
        topBar = { PatientListTopBar() },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add, contentDescription = "Agregar Paciente", tint = MaterialTheme.colorScheme.onPrimary)
            }
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                uiState.loading -> LoadingIndicator()
                uiState.error != null -> ErrorText(message = uiState.error)
                else -> {
                    PatientList(
                        patients = uiState.data,
                        navigateToDetail = navigateToDetail,
                        onDelete = { id -> viewModel.deletePatient(id) }
                    )
                }
            }
        }
    }

    if (showDialog) {
        AddPatientDialog(
            onDismiss = { showDialog = false },
            viewModel = viewModel
        )
    }
}

@Composable
fun PatientList(
    patients: List<Patient>,
    navigateToDetail: (id: Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(patients) { patient ->
            PatientItem(patient = patient, onClick = { navigateToDetail(patient.id) }, onDelete = { onDelete(patient.id) })
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListTopBar() {
    CenterAlignedTopAppBar(
        title = { Text(text = "Lista de Pacientes", style = MaterialTheme.typography.headlineSmall) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientItem(
    patient: Patient,
    onClick: () -> Unit,
    onDelete: () -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    ElevatedCard(
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "${patient.firstName} ${patient.lastName}",
                    style = MaterialTheme.typography.titleMedium
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AccountCircle, contentDescription = "Identificación", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "${patient.identificationType}: ${patient.identification}")
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = "Contacto", tint = MaterialTheme.colorScheme.primary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = patient.emergencyContact)
                }
            }
            IconButton(onClick = { showDialog = true }) {
                Icon(Icons.Default.Delete, contentDescription = "Eliminar", tint = MaterialTheme.colorScheme.error)
            }
        }
    }

    if (showDialog) {
        DeleteConfirmDialog(
            message = "¿Seguro que deseas eliminar a ${patient.firstName}?",
            ok = {
                onDelete()
                showDialog = false
            },
            cancel = { showDialog = false }
        )
    }
}
