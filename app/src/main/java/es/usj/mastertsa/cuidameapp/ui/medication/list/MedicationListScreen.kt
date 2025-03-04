package es.usj.mastertsa.cuidameapp.ui.medication.list

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.ui.medication.add.MedicationAddScreen

@Composable
fun MedicationListScreen(
    viewModel: MedicationListViewModel = viewModel(factory = MedicationListViewModel.factory(
        LocalContext.current)),
    navigateToDetail:(id: Long) -> Unit
){

    val uiState = viewModel.uiState

    LaunchedEffect(Unit) {
        viewModel.getAllMedications()
    }

    Scaffold(
        topBar = { MedicationListTopBar(context = LocalContext.current) }
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
                    MedicationList(
                        medications = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicationListTopBar(context: Context) {
    var showDialog by remember { mutableStateOf(false) }

    val viewModel: MedicationListViewModel = viewModel(
        factory = MedicationListViewModel.factory(context)
    )

    TopAppBar(
        title = { Text(text = "Lista de medicamentos") },
        actions = {
            IconButton(onClick = { showDialog = true }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar Paciente")
            }
        }
    )

    if (showDialog) {
        MedicationAddScreen(onDismiss = { showDialog = false })
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
fun MedicationList(
    medications: List<Medication>,
    navigateToDetail: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(medications) { medication ->
            MedicationItem(medication = medication) {
                navigateToDetail(medication.id)
            }
        }
    }
}

@Composable
fun MedicationItem(medication: Medication, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = medication.name)
            Text(text = medication.description)
            Text(text = medication.administrationType.toString())

        }
    }
}