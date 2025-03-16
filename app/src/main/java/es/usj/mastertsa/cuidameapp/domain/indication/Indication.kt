package es.usj.mastertsa.cuidameapp.domain.indication

data class Indication(
    val id: Long,
    val patientId: Long,
    val medicineId: Int,
    val recurrenceId: String,
    val startDate: String,
    //val hour: String,
    val dosage: Int,
    //val dosages: List<Dosage>
)


enum class AdministrationType{
    Oral, Intravenous, Intramuscular, Subcutaneous, Rectal, Vaginal
}


enum class RecurrenceType{
    Diario, Semanal, Intramuscular, Subcutaneous, Rectal, Vaginal
}