package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity

@Entity(tableName = "IndicationRoom",
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
    @PrimaryKey(autoGenerate = true) val id: Long,
    val patientId:Long,
    val medicineId: Int,
    val recurrence: String, //every 4 hour, every day, week
    val startDate: String,
    //val startHour: String,
    val dosage: Int // 1,2,3 for recurrence
)
