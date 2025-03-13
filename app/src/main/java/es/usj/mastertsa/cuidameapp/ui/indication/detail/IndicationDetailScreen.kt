package es.usj.mastertsa.cuidameapp.ui.indication.detail

import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.share.Util.Companion.calculateDays
import es.usj.mastertsa.cuidameapp.ui.shared.DetailTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator

@Composable
fun IndicationDetailScreen(
    viewModel: IndicationDetailViewModel = viewModel(
        factory = IndicationDetailViewModel.factory(LocalContext.current)
    ),
    navigateBack:()-> Unit
){
    val uiState = viewModel.uiState

//    LaunchedEffect(Unit) {
//        viewModel.getIndicationById(id)
//    }

    Scaffold(
        topBar = { DetailTopBar("Detalle de la indicación", navigateBack) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when {
                uiState.loading -> {
                    LoadingIndicator()
                }
                uiState.error != null -> {
                    ErrorText(message = uiState.error)
                }
                else -> {
                    uiState.data?.let {
                        Column {
                            // Title section
                            Text(
                                text = "Indicación",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp, top = 20.dp) // Space between title and details
                            )
                            IndicationDetailCard(it)
                            Spacer(modifier = Modifier.height(16.dp))

                            // Dosages List
                            Text(
                                text = "Dosis",
                                style = MaterialTheme.typography.headlineSmall,
                                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                            )

                            // Display the list of dosages
                            LazyColumn(
                                modifier = Modifier.padding(16.dp)
                            ) {
                                items(uiState.dosages) { dosage ->
                                    DosageItem(
                                        dosage = dosage,
                                        onComplete = { viewModel.markDosageAsComplete(dosage.id) },
                                        onDelete = { viewModel.deleteDosage(dosage.id) }
                                    )
                                }
                            }
                        }

                    }
                }
            }
        }
    }
}

@Composable
fun IndicationDetailCard(indicationDetail: IndicationDetail) {
    // Card to display the indication details
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp), // Padding around the card
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp), // Elevation to give the card a floating effect
        shape = MaterialTheme.shapes.medium // Rounded corners
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Padding inside the card
        ) {

            // Displaying the patient name
            DetailRow(label = "Paciente:", value = indicationDetail.patientName)

            // Displaying the medicine name
            DetailRow(label = "Medicamento:", value = indicationDetail.medicineName)

            // Displaying the start date
            DetailRow(label = "Fecha inicial:", value = indicationDetail.startDate)

            // Displaying the dosage
            DetailRow(label = calculateDays(indicationDetail.dosage, indicationDetail.recurrenceId), value = indicationDetail.dosage.toString())

            // Displaying the hour
            DetailRow(label = "Hour:", value = indicationDetail.hour)

            // Additional styling can be added here like buttons or actions if needed
        }
    }
}

// Composable for displaying a label and value in a row
@Composable
fun DetailRow(modifier: Modifier = Modifier, label: String, value: String) {
    Row(
        modifier = modifier
            .padding(vertical = 4.dp), // Space between rows
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.weight(1f)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.weight(2f)
        )
    }
}
