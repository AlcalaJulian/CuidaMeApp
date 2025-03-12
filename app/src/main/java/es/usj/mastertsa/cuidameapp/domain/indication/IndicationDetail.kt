package es.usj.mastertsa.cuidameapp.domain.indication

data class IndicationDetail(
    val id: Long,
    val patientId: Long,
    val medicineId: Int,
    val recurrenceId: String,
    val startDate: String,
    val dosage: Int,
    val patientName: String,
    val medicineName: String,
    val hour: String
)