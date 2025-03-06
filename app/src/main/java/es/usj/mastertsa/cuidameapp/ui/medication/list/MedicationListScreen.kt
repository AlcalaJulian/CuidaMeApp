package es.usj.mastertsa.cuidameapp.ui.medication.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.ui.medication.add.MedicationAddScreen
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.ListTopBar
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator

@Composable
fun MedicationListScreen(
    viewModel: MedicationListViewModel = viewModel(factory = MedicationListViewModel.factory(
        LocalContext.current)),
    navigateToDetail:(id: Long) -> Unit
){

    val uiState = viewModel.uiState
    var showDialog by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        viewModel.getAllMedications()
    }

    Scaffold(
        topBar = {
            ListTopBar("Lista de medicamentos", {showDialog =true})}
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
                    MedicationList(
                        medications = uiState.data,
                        navigateToDetail = navigateToDetail
                    )
                    if (showDialog) {
                        MedicationAddScreen(onDismiss = { showDialog = false }, onSuccess = {
                            viewModel.getAllMedications()
                            showDialog = false
                        })
                    }
                }
            }
        }
    }
}


@Composable
fun MedicationList(
    medications: List<Medication>,
    navigateToDetail: (id: Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(medications) { medication ->
            MedicationItem(medication = medication) {
                navigateToDetail(medication.id)
            }
        }
    }
}

@Composable
fun MedicationItem(medication: Medication, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable { onClick() }
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Text(text = medication.name)
            Text(text = medication.description)
            Text(text = medication.administrationType.toString())

        }
    }
}