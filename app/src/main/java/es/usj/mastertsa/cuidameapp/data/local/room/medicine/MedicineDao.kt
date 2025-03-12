package es.usj.mastertsa.cuidameapp.data.local.room.medicine

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicineDao {

    @Query("Select * FROM MedicationRoom")
    fun getAllMedications(): Flow<List<MedicineEntity>>

    @Query("Select * FROM MedicationRoom where id = :id")
    suspend fun getMedicationById(id: Long): MedicineEntity

    @Insert(onConflict = IGNORE)
    suspend fun insertMedications(list: List<MedicineEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicineEntity)

    @Update
    suspend fun updateMedication(medication: MedicineEntity)

    @Query("DELETE FROM MedicationRoom WHERE id = :id")
    suspend fun deleteMedicationById(id: Long)

    @Query("DELETE FROM MedicationRoom")
    suspend fun deleteAllMedications()

    @Query("SELECT * FROM MedicationRoom WHERE name LIKE :name || '%'")
    fun searchMedicationsByName(name: String): Flow<List<MedicineEntity>>

    @Query("SELECT COUNT(*) FROM MedicationRoom")
    suspend fun getMedicationCount(): Int

}