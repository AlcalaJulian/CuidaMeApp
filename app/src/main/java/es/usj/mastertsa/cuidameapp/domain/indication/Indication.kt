package es.usj.mastertsa.cuidameapp.domain.indication

data class Indication(
    val id: Long,
    val medicationId: Int,
    val recurrenceId: String,
    val startDate: String,
    val dosage: Int
)