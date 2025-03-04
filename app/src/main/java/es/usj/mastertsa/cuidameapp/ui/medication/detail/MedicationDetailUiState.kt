package es.usj.mastertsa.cuidameapp.ui.medication.detail

import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail

data class MedicationDetailUiState(
    val loading: Boolean = false,
    val data: MedicationDetail? = null,
    val error: String? = null
)
