package es.usj.mastertsa.cuidameapp.ui.indication.detail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
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
                    uiState.data?.let {  }
                }
            }
        }
    }
}