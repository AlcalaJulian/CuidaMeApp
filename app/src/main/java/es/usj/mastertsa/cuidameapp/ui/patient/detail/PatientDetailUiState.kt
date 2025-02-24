package es.usj.mastertsa.cuidameapp.ui.patient.detail

import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

data class PatientDetailUiState(
    val loading: Boolean = false,
    val data: IndicationDetail? = null,
    val error: String? = null
)
