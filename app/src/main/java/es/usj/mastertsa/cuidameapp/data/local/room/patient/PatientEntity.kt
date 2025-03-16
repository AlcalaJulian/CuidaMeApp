package es.usj.mastertsa.cuidameapp.data.local.room.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PatientRoom")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val identification: String = "",
    val identificationType: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val birthDate: String = "",
    val emergencyContact: String = ""
) {
    constructor() : this(0, "", "", "", "", "", "")
}




