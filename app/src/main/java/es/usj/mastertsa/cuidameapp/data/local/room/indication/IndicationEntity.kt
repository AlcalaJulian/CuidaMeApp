package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "IndicationRoom")
data class IndicationEntity(
    @PrimaryKey val id: Long,
    val medicationId: Int,
    val recurrenceId: String,
    val startDate: String,
    val dosage: Int
)
