package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity

@Entity(
    tableName = "IndicationRoom",
    foreignKeys = [
        ForeignKey(
            entity = PatientEntity::class,
            parentColumns = ["id"],
            childColumns = ["patientId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = MedicineEntity::class,
            parentColumns = ["id"],
            childColumns = ["medicineId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class IndicationEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var patientId: Long = 0,
    var medicineId: Int = 0,
    var recurrence: String = "", // Ejemplo: "every 4 hours"
    var startDate: String = "", // Ejemplo: "2025-03-17"
    var dosage: Int = 1 // Valor por defecto de dosis mínima
)