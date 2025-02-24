package es.usj.mastertsa.cuidameapp.ui.indication.detail

import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

data class IndicationDetailUiState(
    val loading: Boolean = false,
    val data: IndicationDetail? = null,
    val error: String? = null
)
