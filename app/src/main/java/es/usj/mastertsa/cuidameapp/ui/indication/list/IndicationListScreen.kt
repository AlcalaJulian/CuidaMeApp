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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.ui.indication.add.DosageRow
import es.usj.mastertsa.cuidameapp.ui.shared.CustomDropdown
import es.usj.mastertsa.cuidameapp.ui.shared.DatePickerField
import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.ListTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.SwipeBox
import es.usj.mastertsa.cuidameapp.ui.shared.TimePickerField
import java.time.LocalDate

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

                                    IndicationItem(
                                        indication,
                                        onClick = { navigateToDetail(indication.id) },
                                        onDelete = { id -> viewModel.deleteIndication(id) }
                                    )
                                }
                            }
                        } else {
                            Text(text = "No hay indicaciones registradas.")
                            Spacer(modifier = Modifier.height(16.dp))

                            Button(
                                onClick = {

                                    viewModel.getAllPatients()
                                    viewModel.getAllMedications()

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
    }

    if (showAddIndicationDialog) {
        AddIndicationDialog(
            onDismiss = { showAddIndicationDialog = false },
            onConfirm = { newIndication, dosages ->
                viewModel.addIndicationAndRecurrences(newIndication, dosages)
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
                    Text(text = indication.medicineName, style = MaterialTheme.typography.bodySmall)
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

@SuppressLint("NewApi")
@Composable
fun AddIndicationDialog(
    onDismiss: () -> Unit,
    onConfirm: (Indication, List<Dosage>) -> Unit,
    patients: List<Patient>,
    medications: List<Medicine>
) {
    var selectedPatientId by remember { mutableStateOf<Long?>(null) }
    var selectedMedicationId by remember { mutableStateOf<Long?>(null) }

    var expandedPatient by remember { mutableStateOf(false) }
    var expandedMedication by remember { mutableStateOf(false) }

    var recurrenceId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    val recurrenceOptions = listOf("Every 4 hours", "Every day", "Weekly")
    var expandedRecurrence by remember { mutableStateOf(false) }

    var dosages by remember { mutableStateOf(listOf<Dosage>()) }
    var selectedDosage by remember { mutableStateOf<Dosage?>(null) }


    // Function to add a new dosage entry to the list
    fun addDosage(dosage: Dosage) {
        dosages = if (selectedDosage == null) {
            dosages + dosage // Add a new dosage entry with empty fields
        }else{
            dosages.map {
                if (it.id == selectedDosage?.id) dosage else it
            }
        }
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
            dosages = listOf()
                           },
        confirmButton = {
            TextButton(
                onClick = {
                    val medicationAsInt = (selectedMedicationId ?: 0L).toInt()

                    val indication = Indication(
                        id = 0L,
                        patientId = selectedPatientId ?: 0L,
                        medicineId = medicationAsInt,
                        recurrenceId = recurrenceId,
                        startDate = startDate,
                        dosage = dosages.size
                    )
                    onConfirm(indication, dosages)
                }
            ) {
                Text("Agregar")
            }
        },
        dismissButton = {
            TextButton(onClick = {
                onDismiss()
                dosages = listOf()
            }) {
                Text("Cancelar", color = Color.Red)
            }
        },
        title = { Text("Agregar Indicación") },
        text = {
            Column {

                CustomDropdown(
                    items = patients,
                    selectedItem = patients.find { it.id == selectedPatientId },
                    label = "Paciente",
                    onItemSelected = { selectedPatientId = it.id  },
                    expanded = expandedPatient,
                    onExpandedChange = { expandedPatient = it },
                    itemLabel = { "${it.firstName} ${it.lastName}" },
                    noItemsText = "No hay pacientes en la base de datos"
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    items = medications,
                    selectedItem = medications.find { it.id == selectedMedicationId },
                    label = "Medicamento",
                    onItemSelected = { selectedMedicationId = it.id },
                    expanded = expandedMedication,
                    onExpandedChange = { expandedMedication = it},
                    itemLabel = { it.name ?: "" },
                    noItemsText = "No hay medicamentos disponibles"
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    items = recurrenceOptions,
                    selectedItem = recurrenceOptions.find { it == recurrenceId },
                    label = "Recurrencia",
                    onItemSelected = { recurrenceId = it },
                    expanded = expandedRecurrence,
                    onExpandedChange = { expandedRecurrence = it},
                    itemLabel = { it ?: "" },
                    noItemsText = "No hay recurrencias disponibles"
                )

                OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    maxLines = 1,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                )

                DatePickerField(
                    startDate,
                    minDate = LocalDate.now(),
                    label = "Fecha de inicio",
                ) {
                    startDate = it
                }

                if (dosages.isEmpty()){
                    addDosage(Dosage(0, 0, "",""))
                    selectedDosage = dosages.first()
                }

                // Display and manage each dosage entry
                dosages.forEachIndexed { index, dosage ->
                    if (selectedDosage != dosage) {
                        DosageRow(dosage, {
                            selectedDosage = it
                        }) {
                            dosages = dosages.toMutableList().apply { removeAt(index) }
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    }else{
                        DosageField(
                            selectedDosage,
                            cancel = {
                                selectedDosage = null
                            },
                            addDosage = {
                                addDosage(it)
                                selectedDosage = null
                            }
                        )
                    }
                }

                if (selectedDosage == null) {
                    TextButton(onClick =  {
                        selectedDosage = Dosage(0,0,"","")
                        addDosage(selectedDosage!!)
                    }){
                        Text("Agregar dosis")
                    }
                }

            }
        }
    )
}



@Composable
fun DosageField(
    dosage: Dosage?,
    cancel:() -> Unit,
    addDosage: (Dosage) -> Unit
) {
    var quantity by remember { mutableStateOf(dosage?.quantity ?: "") }
    var hour by remember { mutableStateOf(dosage?.hour ?: "") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
        ){
            // Quantity Field
            OutlinedTextField(
                value = quantity,
                onValueChange = { quantity = it},
                label = { Text("Dosis") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                maxLines = 1,
                modifier = Modifier
                    .weight(1f) // Take up remaining space in the row
                    .padding(vertical = 8.dp).padding(end = 8.dp) // Padding around the TextField
            )

            // Time Picker Field
            TimePickerField(
                selectedTime = hour,
                onTimeSelected = { hour = it},
                modifier = Modifier
                    .weight(2f) // Take up remaining space in the row
                    .padding(vertical = 8.dp) // Padding around the TimePicker
            )

        }


        Row(modifier = Modifier.fillMaxWidth()) {
            if (hour.isNotEmpty() || quantity.isNotEmpty()) {
                Button(
                    onClick = {
                        hour = ""
                        quantity = ""
                        cancel()
                    },
                    modifier = Modifier
                        .weight(1f)
                        .padding(top = 2.dp) // Padding around the button
                ) {
                    Text("Cancelar")
                }
            }

            // Add Dosage Button
            Button(
                onClick = {
                    if (quantity.isNotEmpty() && hour.isNotEmpty()){
                        addDosage(Dosage(dosage?.id ?: 0, dosage?.indicationId ?: 0, quantity, hour))
                        hour = ""
                        quantity = ""
                    }
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(top = 2.dp, start = 4.dp) // Padding around the button
            ) {
                Text("Agregar")
            }
        }
    }

}
