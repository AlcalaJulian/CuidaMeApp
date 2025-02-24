package es.usj.mastertsa.cuidameapp.ui.indication.list

import es.usj.mastertsa.cuidameapp.domain.indication.Indication

data class IndicationListUiState(
    val loading: Boolean = false,
    val data: List<Indication> = emptyList(),
    val error: String? = null
)
