package es.usj.mastertsa.cuidameapp.ui.patient

import es.usj.mastertsa.cuidameapp.domain.indication.Indication

data class PatientListUiState(
    val loading: Boolean = false,
    val data: List<Indication> = emptyList(),
    val error: String? = null
)
