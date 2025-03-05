package es.usj.mastertsa.cuidameapp.ui.indication.list

import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.patient.Patient

data class IndicationListUiState(
    val loading: Boolean = false,
    val data: List<Indication> = emptyList(),
    val error: String? = null,
    val success: Boolean = false,
    val patientList: List<Patient> = emptyList(),
    val medicationsList: List<Medication> = emptyList(),

    )
