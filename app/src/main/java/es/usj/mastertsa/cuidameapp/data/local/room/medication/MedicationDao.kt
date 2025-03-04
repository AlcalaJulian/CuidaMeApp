package es.usj.mastertsa.cuidameapp.data.local.room.medication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MedicationDao {

    @Query("Select * FROM MedicationRoom")
    fun getAllMedications(): Flow<List<MedicationEntity>>

    @Query("Select * FROM MedicationRoom where id = :id")
    suspend fun getMedicationById(id: Long): MedicationEntity

    @Insert(onConflict = IGNORE)
    suspend fun insertMedications(list: List<MedicationEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMedication(medication: MedicationEntity)

    @Update
    suspend fun updateMedication(medication: MedicationEntity)

    @Query("DELETE FROM MedicationRoom WHERE id = :id")
    suspend fun deleteMedicationById(id: Long)

    @Query("DELETE FROM MedicationRoom")
    suspend fun deleteAllMedications()

    @Query("SELECT * FROM MedicationRoom WHERE name LIKE :name || '%'")
    fun searchMedicationsByName(name: String): Flow<List<MedicationEntity>>

    @Query("SELECT COUNT(*) FROM MedicationRoom")
    suspend fun getMedicationCount(): Int

}