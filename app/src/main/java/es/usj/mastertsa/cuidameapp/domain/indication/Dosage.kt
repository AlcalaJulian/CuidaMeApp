package es.usj.mastertsa.cuidameapp.domain.indication

data class Dosage(
    val id: Long = 0,
    val indicationId: Long = 0,
    val quantity: String = "",
    val hour: String = "",
    val complete: Boolean = false
)
