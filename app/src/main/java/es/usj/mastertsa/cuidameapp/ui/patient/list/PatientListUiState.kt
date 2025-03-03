package es.usj.mastertsa.cuidameapp.ui.patient.list

import es.usj.mastertsa.cuidameapp.domain.patient.Patient

data class PatientListUiState(
    val loading: Boolean = false,
    val data: List<Patient> = emptyList(),
    val error: String? = null,
    val success: Boolean = false

)
