package es.usj.mastertsa.cuidameapp.ui.patient.list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.ui.shared.DatePickerField
import java.time.LocalDate


@SuppressLint("NewApi")
@Composable
fun AddPatientDialog(
    onDismiss: () -> Unit,
    viewModel: PatientListViewModel = viewModel()
) {
    var identification by remember { mutableStateOf("") }
    var identificationType by remember { mutableStateOf("") }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var emergencyContact by remember { mutableStateOf("") }

    val uiState = viewModel.patients

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Agregar Paciente", style = MaterialTheme.typography.headlineSmall)

                PatientTextField(label = "Identificación", value = identification, onValueChange = { identification = it })
                PatientTextField(
                    label = "Tipo de Identificación",
                    value = identificationType,
                    onValueChange = { identificationType = it },
                    keyboardType = KeyboardType.Number
                )
                PatientTextField(label = "Nombre", value = firstName, onValueChange = { firstName = it })
                PatientTextField(label = "Apellido", value = lastName, onValueChange = { lastName = it })
                //PatientTextField(label = "Fecha de Nacimiento (YYYY-MM-DD)", value = birthDate, onValueChange = { birthDate = it })
                DatePickerField(
                    birthDate,
                    maxDate = LocalDate.now(),
                    label = "Fecha de Nacimiento",
                ) {
                    birthDate = it
                }
                PatientTextField(label = "Contacto de Emergencia", value = emergencyContact, onValueChange = { emergencyContact = it })

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancelar")
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (identification.isNotBlank() && identificationType.isNotBlank() &&
                                firstName.isNotBlank() && lastName.isNotBlank() &&
                                birthDate.isNotBlank() && emergencyContact.isNotBlank()
                            ) {
                                viewModel.addPatient(
                                    Patient(
                                        id = 0L,
                                        identification = identification,
                                        identificationType = identificationType.toInt(),
                                        firstName = firstName,
                                        lastName = lastName,
                                        birthDate = birthDate,
                                        emergencyContact = emergencyContact
                                    )
                                )
                                onDismiss()
                            }
                        }
                    ) {
                        if (uiState.loading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text("Agregar")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                PatientMessage(uiState = uiState)
            }
        }
    }
}

@Composable
fun PatientTextField(label: String, value: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun PatientMessage(uiState: PatientListUiState) {
    when {
        uiState.success -> Text(text = "Paciente agregado con éxito!")
        uiState.error != null -> Text(text = "Error: ${uiState.error}")
    }
}
