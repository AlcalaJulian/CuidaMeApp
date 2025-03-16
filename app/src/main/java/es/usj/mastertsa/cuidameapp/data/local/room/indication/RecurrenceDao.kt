package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity

@Dao
interface RecurrenceDao {

    @Query("Select * FROM RecurrenceRoom where indicationId = :id")
    suspend fun getRecurrencesForIndication(id: Long): List<RecurrenceEntity>

    @Query("DELETE FROM RecurrenceRoom WHERE id = :id")
    suspend fun deleteRecurrenceById(id: Long)

    @Query("DELETE FROM RecurrenceRoom")
    suspend fun deleteAllRecurrences()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecurrences(list: List<RecurrenceEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecurrence(recurrence: RecurrenceEntity):Long

    @Query("DELETE FROM RecurrenceRoom WHERE id = :id")
    suspend fun deleteRecurrence(id: Long)

    @Query("UPDATE RecurrenceRoom SET completed = :complete WHERE id = :id")
    suspend fun updateDosageCompletion(id: Long, complete: Boolean)
}