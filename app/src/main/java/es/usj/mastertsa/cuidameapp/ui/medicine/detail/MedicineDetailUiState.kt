package es.usj.mastertsa.cuidameapp.ui.medicine.detail

import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail

data class MedicineDetailUiState(
    val loading: Boolean = false,
    val data: MedicineDetail? = null,
    val error: String? = null
)
