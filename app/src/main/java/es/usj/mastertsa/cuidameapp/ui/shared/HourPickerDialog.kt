package es.usj.mastertsa.cuidameapp.ui.shared

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@SuppressLint("DefaultLocale")
@Composable
fun HourPickerDialog(
    isDialogOpen: Boolean,
    onDismiss: () -> Unit,
    onTimeSelected: (String) -> Unit
) {
    if (isDialogOpen) {
        var selectedHour by remember { mutableIntStateOf(0) }
        var selectedMinute by remember { mutableIntStateOf(0) }
        val hours = 1..24
        var expandedHour by remember { mutableStateOf(false) }

        val minutes = listOf("Every 4 hours", "Every day", "Weekly")
        var expandedMinuts by remember { mutableStateOf(false) }
        // Dialog to pick hour and minute
        AlertDialog(
            onDismissRequest = onDismiss,
            title = {
                Text(text = "Select Time")
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {


                    // Hour Slider
                    Text(text = "Hora")
                    CustomDropdown(
                        items = hours.map { it },
                        selectedItem = hours.map { it }.find { it == selectedHour },
                        label = "Hora",
                        onItemSelected = { selectedHour = it  },
                        expanded = expandedHour,
                        onExpandedChange = { expandedHour = it },
                        itemLabel = { it.toString() },
                        noItemsText = "No hay horas disponibles"
                    )
//                    Slider(
//                        value = selectedHour.toFloat(),
//                        onValueChange = { selectedHour = it.toInt() },
//                        valueRange = 0f..23f,
//                        steps = 22,
//                        modifier = Modifier.fillMaxWidth()
//                    )

                    // Minute Slider
                    Text(text = "Minutos")
//                    Slider(
//                        value = selectedMinute.toFloat(),
//                        onValueChange = { selectedMinute = it.toInt() },
//                        valueRange = 0f..59f,
//                        steps = 58,
//                        modifier = Modifier.fillMaxWidth()
//                    )
                    CustomDropdown(
                        items = minutes,
                        selectedItem = minutes.find { it == selectedMinute.toString() },
                        label = "Hora",
                        onItemSelected = { selectedMinute = it.toInt() },
                        expanded = expandedMinuts,
                        onExpandedChange = { expandedMinuts = it },
                        itemLabel = { it.toString() },
                        noItemsText = "No hay minutos disponibles"
                    )


                }
            },
            confirmButton = {
                // Confirm button
                Button(onClick = {
                    val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                    onTimeSelected(formattedTime)
                    onDismiss()  // Close the dialog
                }) {
                    Text("Select Time")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("Cancel")
                }
            }
        )
    }
}