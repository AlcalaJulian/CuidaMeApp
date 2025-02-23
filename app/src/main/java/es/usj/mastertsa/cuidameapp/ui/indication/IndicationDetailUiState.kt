package es.usj.mastertsa.cuidameapp.ui.indication

import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

data class IndicationDetailUiState(
    val loading: Boolean = false,
    val data: IndicationDetail? = null,
    val error: String? = null
)
