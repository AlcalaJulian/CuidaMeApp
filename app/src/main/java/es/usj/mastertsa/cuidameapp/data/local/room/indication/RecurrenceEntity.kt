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
        )
    ]
)
data class RecurrenceEntity(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    var indicationId: Long = 0,
    var quantity: Int = 1, // Mínima cantidad por defecto
    var specificDate: String? = "", // Se usa "" en lugar de `null` para evitar errores de deserialización
    var hour: String = "", // Ejemplo: "08:00 AM"
    var completed: Boolean = false // Estado inicial no completado
)
