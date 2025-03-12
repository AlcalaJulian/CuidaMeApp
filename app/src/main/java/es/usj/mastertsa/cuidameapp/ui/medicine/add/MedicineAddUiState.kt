package es.usj.mastertsa.cuidameapp.ui.medicine.add

data class MedicineAddUiState(
    val loading: Boolean = false,
    val error: String? = null,
    val success: Boolean = false
) {
}
