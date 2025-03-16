package es.usj.mastertsa.cuidameapp.ui.indication.list

import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.patient.Patient

data class IndicationListUiState(
    val loading: Boolean = false,
    val data: List<IndicationDetail> = emptyList(),
    val error: String? = null,
    val success: Boolean = false,
    val patientList: List<Patient> = emptyList(),
    val medicationsList: List<Medicine> = emptyList(),
    )
