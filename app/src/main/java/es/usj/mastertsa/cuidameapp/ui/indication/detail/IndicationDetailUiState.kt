package es.usj.mastertsa.cuidameapp.ui.indication.detail

import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence

data class IndicationDetailUiState(
    val loading: Boolean = false,
    val data: IndicationDetail? = null,
    val dosages: List<Recurrence> = emptyList(),
    val error: String? = null
)
