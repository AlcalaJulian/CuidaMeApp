package es.usj.mastertsa.cuidameapp.ui.patient.detail

import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail

data class PatientDetailUiState(
    val loading: Boolean = false,
    val indicationList: List<IndicationDetail> = emptyList() ,
    val patient: PatientDetail? = null,
    val error: String? = null
)
