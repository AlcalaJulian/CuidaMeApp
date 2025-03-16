package es.usj.mastertsa.cuidameapp.domain.patient

data class PatientDetail(
    val id: Long,
    val identification: String,
    val identificationType: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val emergencyContact: String
)