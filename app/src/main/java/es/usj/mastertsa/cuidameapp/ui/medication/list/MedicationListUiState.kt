package es.usj.mastertsa.cuidameapp.ui.medication.list

import es.usj.mastertsa.cuidameapp.domain.indication.Indication

data class MedicationListUiState(
    val loading: Boolean = false,
    val data: List<Indication> = emptyList(),
    val error: String? = null
)
