package es.usj.mastertsa.cuidameapp.data.local.room.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "PatientRoom")
data class PatientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val identification: String,
    val identificationType: String,
    val firstName: String,
    val lastName: String,
    val birthDate: String,
    val emergencyContact: String
)



