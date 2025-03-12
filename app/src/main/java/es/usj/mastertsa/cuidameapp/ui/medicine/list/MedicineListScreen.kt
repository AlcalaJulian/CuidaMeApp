package es.usj.mastertsa.cuidameapp.ui.medicine.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.ui.medicine.add.MedicationAddScreen
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.ListTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator

import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.SwipeBox

@Composable
fun MedicineListScreen(
    viewModel: MedicineListViewModel = viewModel(factory = MedicineListViewModel.factory(
        LocalContext.current)),
    navigateToDetail:(id: Long) -> Unit
){

    val uiState = viewModel.uiState
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getAllMedications()
    }

    Scaffold(
        topBar = {
            ListTopBar("Lista de medicamentos", {showDialog =true})}
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
                    if (uiState.data.isEmpty()){
                        Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                        ) {
                            Text(text = "No hay medicamentos registrados.")
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {
                                    showDialog = true
                                }
                            ) {
                                Text(text = "Agregar medicina")
                            }
                        }

                    }else{
                        MedicineList(
                            medications = uiState.data,
                            navigateToDetail = navigateToDetail,
                            onDelete = { id ->
                                viewModel.deleteMedication(id)
                            }
                        )
                    }

                    if (showDialog) {
                        MedicationAddScreen(onDismiss = { showDialog = false }, onSuccess = {
                            viewModel.getAllMedications()
                            showDialog = false
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun MedicineList(
    medications: List<Medicine>,
    navigateToDetail: (id: Long) -> Unit,
    onDelete: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(medications) { medication ->
            MedicationItem(
                medication = medication,
                onClick = {
                    navigateToDetail(medication.id)
                }
            ) {
                onDelete(medication.id)
            }
        }
    }
}


@Composable
fun MedicationItem(
    medication: Medicine,
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
        //Display the medication item as a card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
        ) {
            Row(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = medication.name,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(text = medication.description, style = MaterialTheme.typography.bodySmall)
                    Text(
                        text = medication.administrationType.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }


    // Confirmation dialog
    if (showDialog) {
        DeleteConfirmDialog(
            message = "",
            ok = {
            onDelete(medication.id)  // Perform the delete action
            showDialog = false // Close the dialog after deletion
        }, cancel = { showDialog = false })
    }
}
