package es.usj.mastertsa.cuidameapp.ui.medication.add

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medication.Medication

@Composable
fun MedicationAddScreen(
    viewModel: MedicationAddViewModel = viewModel(
        factory = MedicationAddViewModel.factory(
            LocalContext.current
        )
    ),
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var administrationType by remember { mutableStateOf("") }

    val uiState = viewModel.uiState

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
                Text(text = "Agregar Medicamento", style = MaterialTheme.typography.headlineSmall)

                MedicationTextField(label = "Nombre", value = name, onValueChange = { name = it })
                MedicationTextField(label = "Descriptión", value = description, onValueChange = { description = it })

                MedicationTextField(label = "Tipo de administración", value = administrationType, onValueChange = { administrationType = it })

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
                            if (name.isNotBlank() && description.isNotBlank() &&
                                administrationType.isNotBlank()
                            ) {
                                viewModel.addMedication(
                                    Medication(
                                        id = 0L,
                                        description = description,
                                        name = name,
                                        administrationType = administrationType.toInt()
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

                MedicationMessage(uiState = uiState)
            }
        }
    }
}

@Composable
fun MedicationTextField(label: String, value: String, onValueChange: (String) -> Unit, keyboardType: KeyboardType = KeyboardType.Text) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier.fillMaxWidth(),
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType)
    )
}

@Composable
fun MedicationMessage(uiState: MedicationAddUiState) {
    when {
        uiState.success -> Text(text = "Paciente agregado con éxito!")
        uiState.error != null -> Text(text = "Error: ${uiState.error}")
    }
}