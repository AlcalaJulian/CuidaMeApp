package es.usj.mastertsa.cuidameapp.data.local.room.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserRoom")
data class UserEntity(
    @PrimaryKey val id: String,
    val firstName: String,
    val lastName: String,
)
