@file:OptIn(ExperimentalMaterial3Api::class)

package es.usj.mastertsa.cuidameapp.ui.shared

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar


@Composable
fun TimePickerField(
    modifier: Modifier = Modifier,
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var timePickerDialog: TimePickerDialog? = null
    val focusRequester = remember { FocusRequester() }
    val currentTime = Calendar.getInstance()
    var showPicker by remember { mutableStateOf(false) }

    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.get(Calendar.HOUR_OF_DAY),
        initialMinute = currentTime.get(Calendar.MINUTE),
        is24Hour = true,
    )

    // Handle time selection dialog when the TextField is focused
    OutlinedTextField(
        value = selectedTime,
        onValueChange = {},
        label = { Text("Hora") },
        readOnly = true,

        modifier = modifier

            .focusRequester(focusRequester) // Attach the FocusRequester here
            .onFocusChanged { focusState ->
                if (focusState.isFocused)
                    showPicker = focusState.isFocused
            }
    )

    if (showPicker){
        AlertDialog(
            onDismissRequest = {
                showPicker = false
            },
            dismissButton = {
                TextButton(onClick = {
                    showPicker = false
                    focusRequester.freeFocus()
                }) {
                    Text("Cerrar")
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    onTimeSelected("${timePickerState.hour}:${timePickerState.minute}")
                    showPicker = false
                    focusRequester.freeFocus()
                }) {
                    Text("OK")
                }
            },
            text = {
                TimePicker(
                    state = timePickerState,
                )
            }
        )
    }
}

@Composable
fun TimePickerDialogs(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    content: @Composable () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        dismissButton = {
            TextButton(onClick = { onDismiss() }) {
                Text("Dismiss")
            }
        },
        confirmButton = {
            TextButton(onClick = { onConfirm() }) {
                Text("OK")
            }
        },
        text = { content() }
    )
}