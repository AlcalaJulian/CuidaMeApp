package es.usj.mastertsa.cuidameapp.ui.medication

import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

data class MedicationDetailUiState(
    val loading: Boolean = false,
    val data: IndicationDetail? = null,
    val error: String? = null
)
