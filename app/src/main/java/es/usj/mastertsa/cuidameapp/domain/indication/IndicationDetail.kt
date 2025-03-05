package es.usj.mastertsa.cuidameapp.domain.indication

data class IndicationDetail(
    val id: Long,
    val patientId:Long,
    val medicationId: Int,
    val recurrenceId: String,
    val startDate: String,
    val dosage: Int
)