package es.usj.mastertsa.cuidameapp.domain.medicine


data class Medicine (
    val id: Long,
    val name: String,
    val description: String,
    val administrationType: String
)