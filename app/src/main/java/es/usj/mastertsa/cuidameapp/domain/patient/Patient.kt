package es.usj.mastertsa.cuidameapp.domain.patient

data class Patient(
    val id: Long,
    val identification: String,
    val identificationType: Int,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val emergencyContact: String
)
