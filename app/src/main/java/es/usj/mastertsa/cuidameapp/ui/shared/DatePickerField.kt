package es.usj.mastertsa.cuidameapp.ui.shared

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar
import java.util.Date

@SuppressLint("NewApi")
@Composable
fun DatePickerField(
    selectedDate: String,
    label: String,
    minDate: LocalDate? = null,
    maxDate: LocalDate? = null,
    onDateSelected: (String) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var datePickerDialog: DatePickerDialog? = null
    val focusRequester = remember { FocusRequester() }

    // Function to convert LocalDate to milliseconds
    @SuppressLint("NewApi")
    fun toMillis(date: LocalDate): Long {
        return date.atStartOfDay(ZoneId.systemDefault())?.toInstant()?.toEpochMilli() ?: 0
    }

    OutlinedTextField(
        value = selectedDate,
        onValueChange = {},
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp)
            .focusRequester(focusRequester) // Attach the FocusRequester here
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    datePickerDialog = DatePickerDialog(
                        context,
                        { _, year, month, dayOfMonth ->
                            val date = "$dayOfMonth-${month + 1}-$year"
                            onDateSelected(date)
                            datePickerDialog?.hide()
                            focusRequester.freeFocus()
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH)
                    )

                    // Apply minDate and maxDate if provided
                    minDate?.let {
                        datePickerDialog?.datePicker?.minDate = toMillis(it)
                    }
                    maxDate?.let {
                        datePickerDialog?.datePicker?.maxDate = toMillis(it)
                    }

                    datePickerDialog?.show()
                }
            },
        readOnly = true,
    )
}