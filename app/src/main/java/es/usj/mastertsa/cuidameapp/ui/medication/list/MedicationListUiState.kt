package es.usj.mastertsa.cuidameapp.ui.medication.list

import es.usj.mastertsa.cuidameapp.domain.medication.Medication

data class MedicationListUiState(
    val loading: Boolean = false,
    val data: List<Medication> = emptyList(),
    val error: String? = null
)
