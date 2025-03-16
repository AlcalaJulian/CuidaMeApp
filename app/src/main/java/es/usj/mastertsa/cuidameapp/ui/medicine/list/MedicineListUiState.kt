package es.usj.mastertsa.cuidameapp.ui.medicine.list

import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine

data class MedicineListUiState(
    val loading: Boolean = false,
    val data: List<Medicine> = emptyList(),
    val error: String? = null
)
