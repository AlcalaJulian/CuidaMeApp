package es.usj.mastertsa.cuidameapp.data.local.room.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MedicationRoom")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val description: String,
    val administrationType: Int
)
