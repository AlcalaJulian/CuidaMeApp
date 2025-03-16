package es.usj.mastertsa.cuidameapp.ui.indication.add

data class IndicationAddUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
