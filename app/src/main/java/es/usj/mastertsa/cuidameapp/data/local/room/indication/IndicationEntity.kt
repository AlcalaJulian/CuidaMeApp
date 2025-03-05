package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IndicationRoom")
data class IndicationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val patientId:Long,
    val medicationId: Int,
    val recurrenceId: String,
    val startDate: String,
    val dosage: Int
)
