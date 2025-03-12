package es.usj.mastertsa.cuidameapp.ui.indication.add

data class MedicationAddUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
)
