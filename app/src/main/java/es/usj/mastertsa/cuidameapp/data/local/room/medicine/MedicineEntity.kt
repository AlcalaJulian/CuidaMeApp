package es.usj.mastertsa.cuidameapp.data.local.room.medicine

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "MedicationRoom")
data class MedicineEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var name: String = "",
    var description: String = "",
    var administrationType: String = ""
)
