package es.usj.mastertsa.cuidameapp.domain.indication

data class Recurrence(
    val id: Long,
    val indicationId: Long,
    val specificDate: String?,
    val quantity: Int,
    val hour: String,
    val completed: Boolean
)