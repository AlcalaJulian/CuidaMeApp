package es.usj.mastertsa.cuidameapp.ui.medicine.add

import androidx.compose.foundation.background
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
import androidx.compose.material3.ButtonColors
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.ui.shared.CustomDropdown

@Composable
fun MedicationAddScreen(
    viewModel: MedicineAddViewModel = viewModel(
        factory = MedicineAddViewModel.factory(
            LocalContext.current
        )
    ),
    onDismiss: () -> Unit,
    onSuccess: () -> Unit,
    existingMedicine: Medicine? = null
) {
    var name by remember { mutableStateOf(existingMedicine?.name ?: "") }
    var description by remember { mutableStateOf(existingMedicine?.description ?: "") }
    var administrationType by remember { mutableStateOf(existingMedicine?.administrationType ?: "") }

    val administrationTypes = listOf("Oral", "Intrabenosa", "Muscular")

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

                //MedicationTextField(label = "Tipo de administración", value = administrationType, onValueChange = { administrationType = it })
                CustomDropdown(
                    items = administrationTypes,
                    selectedItem = administrationType,
                    label = "Vía de administración",
                    onItemSelected = { administrationType = it },
                    itemLabel = { it },
                    noItemsText = "No hay data disponibles",
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonColors(
                            containerColor = Color.Red, contentColor = Color.White,
                            disabledContainerColor = Color.DarkGray,
                            disabledContentColor = Color.Black
                        )
                        ) {
                        Text("Cancelar", color = Color.White)
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                    Button(
                        onClick = {
                            if (name.isNotBlank() && description.isNotBlank() &&
                                administrationType.isNotBlank()
                            ) {
                                viewModel.addMedication(
                                    Medicine(
                                        id = existingMedicine?.id ?: 0L,
                                        description = description,
                                        name = name,
                                        administrationType = administrationType
                                    )
                                )
                                onSuccess()
                            }
                        }
                    ) {
                        if (uiState.loading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        } else {
                            Text("Guardar")

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
fun MedicationMessage(uiState: MedicineAddUiState) {
    when {
        uiState.success -> Text(text = "Paciente agregado con éxito!")
        uiState.error != null -> Text(text = "Error: ${uiState.error}")
    }
}