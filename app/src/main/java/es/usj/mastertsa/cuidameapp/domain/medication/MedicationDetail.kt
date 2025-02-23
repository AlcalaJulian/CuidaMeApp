package es.usj.mastertsa.cuidameapp.domain.medication

data class MedicationDetail(
    val id: Long,
    val name: String,
    val description: String,
    val administrationType: Int
)