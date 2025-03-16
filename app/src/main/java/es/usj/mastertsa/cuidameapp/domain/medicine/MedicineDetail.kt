package es.usj.mastertsa.cuidameapp.domain.medicine

data class MedicineDetail(
    val id: Long,
    val name: String,
    val description: String,
    val administrationType: String
)