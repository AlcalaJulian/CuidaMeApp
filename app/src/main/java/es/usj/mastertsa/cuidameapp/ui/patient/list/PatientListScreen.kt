package es.usj.mastertsa.cuidameapp.ui.patient.list

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator
import es.usj.mastertsa.cuidameapp.ui.shared.SwipeBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListScreen(
    navigateToDetail: (id: Long) -> Unit,
    viewModel: PatientListViewModel = viewModel(
        factory = PatientListViewModel.factory(LocalContext.current)
    )
) {
    val uiState = viewModel.patients

    LaunchedEffect(Unit) {
        viewModel.getAllPatients()
    }

    Scaffold(
        topBar = { PatientListTopBar(context = LocalContext.current) }
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
                    PatientList(
                        patients = uiState.data,
                        navigateToDetail = navigateToDetail,
                        onDelete = { id ->
                            viewModel.deleteMedication(id)
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientListTopBar(context: Context) {
    var showDialog by remember { mutableStateOf(false) }

    val viewModel: PatientListViewModel = viewModel(
        factory = PatientListViewModel.factory(context)
    )

    TopAppBar(
        title = { Text(text = "Lista de Pacientes") },
        actions = {
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Paciente")
            }
        }
    )

    if (showDialog) {
        AddPatientDialog(onDismiss = { showDialog = false }, viewModel = viewModel)
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
        contentPadding = PaddingValues(8.dp)
    ) {
        items(patients) { patient ->
            PatientItem(patient = patient, onClick = {
                navigateToDetail(patient.id)
            }, onDelete = {
                onDelete(patient.id)
            })
        }
    }
}

@Composable
fun PatientItem(
    patient: Patient,
    onClick: () -> Unit,
    onDelete: (Long) -> Unit
) {
    // For managing the confirmation dialog visibility
    var showDialog by remember { mutableStateOf(false) }

    SwipeBox(
        onDelete = {
            showDialog = true
        },
        onEdit = { showDialog = true },
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Row(modifier = Modifier.padding(16.dp)) {
                Column {
                    Text(text = "${ patient.firstName } ${ patient.lastName }")
                    Text(text = patient.identification)
                }
            }
        }
    }
    // Confirmation dialog
    if (showDialog) {
        DeleteConfirmDialog(
            message = "",
            ok = {
                onDelete(patient.id)  // Perform the delete action
                showDialog = false // Close the dialog after deletion
            }, cancel = { showDialog = false })
    }
}
