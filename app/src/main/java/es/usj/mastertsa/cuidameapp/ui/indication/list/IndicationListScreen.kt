package es.usj.mastertsa.cuidameapp.ui.indication.list

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.sharp.Add
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
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
import es.usj.mastertsa.cuidameapp.domain.share.Util.Companion.calculateDays
import es.usj.mastertsa.cuidameapp.ui.indication.add.DosageRow
import es.usj.mastertsa.cuidameapp.ui.indication.detail.DetailRow
import es.usj.mastertsa.cuidameapp.ui.shared.CustomDropdown
import es.usj.mastertsa.cuidameapp.ui.shared.DatePickerField
import es.usj.mastertsa.cuidameapp.ui.shared.DeleteConfirmDialog
import es.usj.mastertsa.cuidameapp.ui.shared.ListTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.SwipeBox
import es.usj.mastertsa.cuidameapp.ui.shared.TimePickerField
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun IndicationListScreen(
    viewModel: IndicationListViewModel = viewModel(
        factory = IndicationListViewModel.factory(LocalContext.current)
    ),
    navigateToDetail: (id: Long) -> Unit,
) {
    val uiState = viewModel.indicationUiState
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
                    Toast.makeText(LocalContext.current, uiState.error, Toast.LENGTH_LONG).show()
                }

                else -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {

                        if (uiState.data.isNotEmpty()) {
                            LazyColumn(
                                modifier = Modifier.weight(1f),
                                verticalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
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
        viewModel.getAllPatients()
        viewModel.getAllMedications()

        AddIndicationDialog(
            onDismiss = { showAddIndicationDialog = false },
            onConfirm = { newIndication, dosages ->
                viewModel.addIndicationAndRecurrences(newIndication, dosages)
                    showAddIndicationDialog = false
                    viewModel.getAllIndications()
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
        onEdit = { onClick() },
    ) {
        //Display the medication item as a card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() },
            elevation = CardDefaults.elevatedCardElevation(12.dp),
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
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.headlineLarge
                    )

                    DetailRow(modifier = Modifier,"Medicamento:", indication.medicineName)
                    DetailRow(modifier = Modifier,"Recurrencia:", "${indication.dosage} ${calculateDays(indication.dosage, indication.recurrenceId)}")
                    DetailRow(modifier = Modifier,"Inicio:",  indication.startDate)
                }
//                Text(
//                    text = indication.startDate,
//                    style = MaterialTheme.typography.bodySmall
//                )

                Icon(
                    Icons.Default.Delete,
                    tint = Color.Red,
                    contentDescription = "",
                    modifier = Modifier
                        .padding(12.dp)
                        .clickable {
                        onDelete(indication.id)
                    }
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddIndicationDialog(
    onDismiss: () -> Unit,
    onConfirm: (Indication, List<Dosage>) -> Unit,
    patients: List<Patient>,
    medications: List<Medicine>
) {
    var selectedPatientId by remember { mutableStateOf<Long?>(null) }
    var selectedMedicationId by remember { mutableStateOf<Long?>(null) }
    var recurrenceId by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    //var selectedDosage by remember { mutableStateOf<Dosage?>(null) }
    var dosages by remember { mutableStateOf(listOf<Dosage>()) }
    val recurrenceOptions = listOf("Cada día", "Cada semana")

    fun addDosage(dosage: Dosage) {
       // dosages = if (selectedDosage == null) {
         dosages = dosages + dosage // Add a new dosage entry
//        } else {
//            dosages.map {
//                if (it == selectedDosage) dosage else it
//            }
//        }
    }

    AlertDialog(
        onDismissRequest = {
            onDismiss()
            dosages = listOf() // Reset dosages when dismissing dialog
        },
        confirmButton = {
            Button(
                onClick = {
                    if (quantity.isNotEmpty() && startDate.isNotEmpty() && recurrenceId.isNotEmpty() && selectedMedicationId != 0L && selectedMedicationId != 0L && dosages.isNotEmpty()) {
                        val medicationAsInt = (selectedMedicationId ?: 0L).toInt()

                        val indication = Indication(
                            id = 0L,
                            patientId = selectedPatientId ?: 0L,
                            medicineId = medicationAsInt,
                            recurrenceId = recurrenceId,
                            startDate = startDate,
                            dosage = quantity.toInt()
                        )
                        onConfirm(
                            indication,
                            dosages.filter { it.hour.isNotEmpty() && it.quantity.isNotEmpty()
                            })
                    }
                }
            ) {
                Text("Guardar")
            }
        },
        dismissButton = {
            Button(
                colors = ButtonColors(
                    containerColor = Color.Red, contentColor = Color.White,
                    disabledContainerColor = Color.DarkGray,
                    disabledContentColor = Color.Black
                ),
                onClick = {
                onDismiss()
                dosages = listOf() // Reset dosages when cancelling
            }) {
                Text("Cancelar")
            }
        },
        title = { Text("Agregar Indicación") },
        text = {
            Column {
                CustomDropdown(
                    items = patients,
                    selectedItem = patients.find { it.id == selectedPatientId },
                    label = "Paciente",
                    onItemSelected = { selectedPatientId = it.id },
                    itemLabel = {

                            "${it.firstName} ${it.lastName}"

                                },
                    noItemsText = "No hay pacientes en la base de datos"
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    items = medications,
                    selectedItem = medications.find { it.id == selectedMedicationId },
                    label = "Medicamento",
                    onItemSelected = { selectedMedicationId = it.id },
                    itemLabel = { it.name },
                    noItemsText = "No hay medicamentos disponibles"
                )

                Spacer(modifier = Modifier.height(8.dp))

                CustomDropdown(
                    items = recurrenceOptions,
                    selectedItem = recurrenceId,
                    label = "Recurrencia",
                    onItemSelected = { recurrenceId = it },
                    itemLabel = { it },
                    noItemsText = "No hay recurrencias disponibles",
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

                // Add a new dosage entry if dosages is empty
//                if (dosages.isEmpty()) {
//                    addDosage(Dosage())
//                    selectedDosage = dosages.first() // Set the first dosage as selected
//                }

                // Display each dosage entry
                dosages.forEachIndexed { index, dosage ->
                    //if (selectedDosage != dosage && dosage.hour.isNotEmpty() && dosage.quantity.isNotEmpty()) {
                        DosageRow(dosage, {
                            //selectedDosage = it // Set selected dosage when clicked
                        }) {
                            dosages = dosages.toMutableList().apply { removeAt(index) } // Remove dosage on delete
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                    //}
                }

                DosageField(
                    null,
                    cancel = {
                        //selectedDosage = null
                    }, // Reset selected dosage
                    addDosage = {
                        addDosage(it)
                    } // Add new dosage entry
                )
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
                    .weight(1f) // Take up remaining space in the row
                    .padding(vertical = 8.dp) // Padding around the TimePicker
            )
            //Column {
//            Box(modifier = Modifier.padding(horizontal = 4.dp).padding(top = 7.dp).align(Alignment.CenterVertically)) {
//                Icon(
//                    imageVector = Icons.Sharp.Settings,
//                    contentDescription = "Agregar dosis",
//                    modifier = Modifier
//                        .background(Color.Red)
//                        .padding(10.dp)
//                        .clickable {
//                            hour = ""
//                            quantity = ""
//                            cancel()
//                        }
//                )
//            }

            Box(modifier = Modifier
                .align(Alignment.CenterVertically)
                .padding(top = 7.dp, start = 7.dp)) {
                Icon(
                    imageVector = Icons.Sharp.Add,
                    contentDescription = "Select Time",
                    tint = Color.White,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp)
                        .clickable {
                            if (quantity.isNotEmpty() && hour.isNotEmpty()) {
                                addDosage(
                                    Dosage(
                                         0,
                                         0,
                                        quantity,
                                        hour
                                    )
                                )
                                hour = ""
                                quantity = ""
                            }
                        }
                )
            }
           // }

        }
    }

}
