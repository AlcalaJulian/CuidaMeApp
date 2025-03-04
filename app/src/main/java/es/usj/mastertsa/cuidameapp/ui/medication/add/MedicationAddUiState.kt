package es.usj.mastertsa.cuidameapp.ui.medication.add

data class MedicationAddUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
}
