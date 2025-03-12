package es.usj.mastertsa.cuidameapp.ui.shared

import android.annotation.SuppressLint
import android.app.TimePickerDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@SuppressLint("DefaultLocale")
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

    // Handle time selection dialog when the TextField is focused
    OutlinedTextField(
        value = selectedTime,
        onValueChange = {},
        label = { Text("Hora") },
        readOnly = true,
        trailingIcon = {
            Icon(imageVector = Icons.Sharp.Settings, contentDescription = "Select Time")
        },
        modifier = modifier
            .focusRequester(focusRequester) // Attach the FocusRequester here
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    // Open the TimePickerDialog when the TextField is clicked
                    timePickerDialog = TimePickerDialog(
                        context,
                        { _, hourOfDay, minute ->
                            // Format the time as HH:mm
                            val formattedTime = String.format("%02d:%02d", hourOfDay, minute)
                            onTimeSelected(formattedTime)
                            focusRequester.freeFocus()
                            timePickerDialog?.hide() // Close the dialog after selection
                        },
                        calendar.get(Calendar.HOUR_OF_DAY),
                        calendar.get(Calendar.MINUTE),
                        true // Use 24-hour format
                    )
                    timePickerDialog?.show()
                }
            }
    )
}