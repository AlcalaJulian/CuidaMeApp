package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecurrenceDao {
    @Query("DELETE FROM RecurrenceRoom WHERE id = :id")
    suspend fun deleteRecurrenceById(id: Long)

    @Query("DELETE FROM RecurrenceRoom")
    suspend fun deleteAllRecurrences()
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecurrences(list: List<RecurrenceEntity>)
}