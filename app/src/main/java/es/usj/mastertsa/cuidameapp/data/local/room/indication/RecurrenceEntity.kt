package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "RecurrenceRoom",
    foreignKeys = [
    ForeignKey(
        entity = IndicationEntity::class,
        parentColumns = ["id"],
        childColumns = ["indicationId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RecurrenceEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val indicationId: Long,
    val quantity: Int,
    val specificDate: String?,
    val hour: String,
    val completed: Boolean
)