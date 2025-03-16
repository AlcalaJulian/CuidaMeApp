package es.usj.mastertsa.cuidameapp.ui.indication.add

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.wear.compose.material.Text
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage
import es.usj.mastertsa.cuidameapp.domain.indication.Indication

@Composable
fun MedicationAddScreen(
    viewModel: IndicationAddViewModel = viewModel(factory = IndicationAddViewModel.factory(
        LocalContext.current)),
    patientId: Long, // Patient ID passed from navigation
){
    var medicineId by remember { mutableStateOf(0) }
    var recurrence by remember { mutableStateOf("") }
    var startDate by remember { mutableStateOf("") }
    var startHour by remember { mutableStateOf("") }
    var dosage by remember { mutableStateOf(1) }
    var dosages by remember { mutableStateOf(listOf<Dosage>()) }

    fun addDosage() {
        dosages = dosages + Dosage(0,0,"", "") // Add a new dosage entry with empty fields
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Add New Indication", style = MaterialTheme.typography.headlineSmall)

        OutlinedTextField(
            value = medicineId.toString(),
            onValueChange = { medicineId = it.toIntOrNull() ?: 0 },
            label = { Text("Medicine ID") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = recurrence,
            onValueChange = { recurrence = it },
            label = { Text("Recurrence (e.g., every 4 hours)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = startDate,
            onValueChange = { startDate = it },
            label = { Text("Start Date (e.g., 2025-03-12)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = startHour,
            onValueChange = { startHour = it },
            label = { Text("Start Hour (e.g., 08:00 AM)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = dosage.toString(),
            onValueChange = { dosage = it.toIntOrNull() ?: 1 },
            label = { Text("Dosage") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            // Create IndicationEntity and add it via ViewModel
            val indication = Indication(
                id = 0,
                patientId = patientId,
                medicineId = medicineId,
                recurrenceId = recurrence,
                startDate = startDate,
                //hour = startHour,
                dosage = dosage,
                //hour =
            )
            viewModel.addIndicationAndRecurrences(indication, dosages) // You can add recurrences if needed

        }) {
            Text("Save Indication")
        }
    }
}