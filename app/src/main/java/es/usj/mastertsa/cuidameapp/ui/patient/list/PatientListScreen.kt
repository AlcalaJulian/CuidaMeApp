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
                        navigateToDetail = navigateToDetail
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
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize()) {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}


@Composable
fun ErrorText(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Error: $message")
    }
}


@Composable
fun PatientList(
    patients: List<Patient>,
    navigateToDetail: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(patients) { patient ->
            PatientItem(patient = patient) {
                navigateToDetail(patient.id)
            }
        }
    }
}

@Composable
fun PatientItem(patient: Patient, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = patient.firstName)
            Text(text = patient.lastName)
            Text(text = patient.identification)


        }
    }
}
