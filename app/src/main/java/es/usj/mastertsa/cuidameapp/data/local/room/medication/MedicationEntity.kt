package es.usj.mastertsa.cuidameapp.data.local.room.medication

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MedicationRoom")
data class MedicationEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val description: String,
    val administrationType: Int
)
