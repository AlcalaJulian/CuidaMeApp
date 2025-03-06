package es.usj.mastertsa.cuidameapp.ui.indication.list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.ListTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.SwipeBox

@Composable
fun IndicationListScreen(
    viewModel: IndicationListViewModel = viewModel(
        factory = IndicationListViewModel.factory(LocalContext.current)
    ),
    navigateToDetail: (id: Long) -> Unit,
) {
    val uiState = viewModel.indications
    var showAddIndicationDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.getAllIndications()
    }

    Scaffold(
        topBar = { ListTopBar("Lista de indicaciones") { showAddIndicationDialog = true } }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                uiState.loading -> {
                    CircularProgressIndicator()
                }

                uiState.error != null -> {
                    Text(text = "Error: ${uiState.error}")
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {

                        if (uiState.data.isNotEmpty()) {
                            LazyColumn(modifier = Modifier.weight(1f)) {
                                items(uiState.data) { indication ->
//                                    Text(
//                                        text = "• Indicación ID: ${indication.id} " +
//                                                "MedicamentoID: ${indication.medicationId}, " +
//                                                "Inicio: ${indication.startDate}",
//                                        modifier = Modifier.clickable { navigateToDetail(indication.id) }
//                                    )
                                    IndicationItem(
                                        indication,
                                        onClick = { navigateToDetail(indication.id) },
                                        onDelete = { id -> viewModel.deleteIndication(id) }
                                    )
                                }
                            }
                        } else {
                            Text(text = "No hay indicaciones registradas.")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Botón que abre el diálogo de agregar indicación
                        Button(
                            onClick = {
                                // Cargamos lista de pacientes y medicamentos
                                viewModel.getAllPatients()
                                viewModel.getAllMedications()
                                // Mostramos el diálogo
                                showAddIndicationDialog = true
                            }
                        ) {
                            Text(text = "Agregar Indicación")
                        }

                        if (uiState.success) {
                            Text(text = "¡Indicación agregada con éxito!")
                        }
                    }
                }
            }
        }
    }

    if (showAddIndicationDialog) {
        AddIndicationDialog(
            onDismiss = { showAddIndicationDialog = false },
            onConfirm = { newIndication ->
                viewModel.addPatient(newIndication)
                showAddIndicationDialog = false
            },
            patients = uiState.patientList,
            medications = uiState.medicationsList
        )
    }
}

@Composable
fun IndicationItem(
    indication: IndicationDetail,
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
                        text = indication.patientName,
                        maxLines = 1,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(text = indication.medicationName, style = MaterialTheme.typography.bodySmall)
                }
                Text(
                    text = indication.startDate,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }


    // Confirmation dialog
    if (showDialog) {
        DeleteConfirmDialog(
            message = "",
            ok = {
                onDelete(indication.id)  // Perform the delete action
                showDialog = false // Close the dialog after deletion
            }, cancel = { showDialog = false })
    }
}

@Composable
fun AddIndicationDialog(
    onDismiss: () -> Unit,
    onConfirm: (Indication) -> Unit,
    patients: List<Patient>,
    medications: List<Medication>
) {
    var selectedPatientId by remember { mutableStateOf<Long?>(null) }
    var selectedMedicationId by remember { mutableStateOf<Long?>(null) }

    var expandedPatient by remember { mutableStateOf(false) }
    var expandedMedication by remember { mutableStateOf(false) }

    var recurrenceId by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        confirmButton = {
            TextButton(
                onClick = {
                    val medicationAsInt = (selectedMedicationId ?: 0L).toInt()

                    val indication = Indication(
                        id = 0L,
                        patientId = selectedPatientId ?: 0L,
                        medicationId = medicationAsInt,
                        recurrenceId = recurrenceId,
                        startDate = startDate,
                        dosage = dosage.toIntOrNull() ?: 0
                    )
                    onConfirm(indication)
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Cancelar")
            }
        },
        title = { Text("Agregar Indicación") },
        text = {
            Column {
                // Dropdown para Pacientes
                ExposedPatientDropdown(
                    patients = patients,
                    selectedPatientId = selectedPatientId,
                    onPatientSelected = { selectedPatientId = it },
                    expanded = expandedPatient,
                    onExpandedChange = { expandedPatient = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Dropdown para Medicamentos
                ExposedMedicationDropdown(
                    medications = medications,
                    selectedMedicationId = selectedMedicationId,
                    onMedicationSelected = { selectedMedicationId = it },
                    expanded = expandedMedication,
                    onExpandedChange = { expandedMedication = it }
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = recurrenceId,
                    onValueChange = { recurrenceId = it },
                    label = { Text("Recurrencia") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = startDate,
                    onValueChange = { startDate = it },
                    label = { Text("Fecha de inicio") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
                OutlinedTextField(
                    value = dosage,
                    onValueChange = { dosage = it },
                    label = { Text("Dosis") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )
            }
        }
    )
}

@SuppressLint("UnrememberedMutableState")
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ExposedPatientDropdown(
    patients: List<Patient>,
    selectedPatientId: Long?,
    onPatientSelected: (Long) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    val selectedPatientText by derivedStateOf {
        val patient = patients.find { it.id == selectedPatientId }
        patient?.let { "${it.firstName} ${it.lastName}" } ?: "Seleccione un paciente"
    }
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) }
    ) {
        TextField(
            value = selectedPatientText,
            onValueChange = {  },
            readOnly = true,
            label = { Text("Paciente") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            if (patients.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No hay pacientes en la base de datos") },
                    onClick = { onExpandedChange(false) }
                )
            } else {
                patients.forEach { patient ->
                    val fullName = "${patient.firstName} ${patient.lastName}"
                    DropdownMenuItem(
                        text = { Text(fullName) },
                        onClick = {
                            onPatientSelected(patient.id)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}
@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExposedMedicationDropdown(
    medications: List<Medication>,
    selectedMedicationId: Long?,
    onMedicationSelected: (Long) -> Unit,
    expanded: Boolean,
    onExpandedChange: (Boolean) -> Unit
) {
    // Texto que se muestra en el TextField
    val selectedMedicationText by derivedStateOf {
        val med = medications.find { it.id == selectedMedicationId }
        med?.name ?: "Seleccione un medicamento"
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange(!expanded) }
    ) {
        // Campo de texto
        TextField(
            value = selectedMedicationText,
            onValueChange = { },
            readOnly = true,
            label = { Text("Medicamento") },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor()
        )

        // Menú desplegable
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            if (medications.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No hay medicamentos disponibles") },
                    onClick = { onExpandedChange(false) }
                )
            } else {
                medications.forEach { medication ->
                    DropdownMenuItem(
                        text = { Text(medication.name) },
                        onClick = {
                            onMedicationSelected(medication.id)
                            onExpandedChange(false)
                        }
                    )
                }
            }
        }
    }
}
