package es.usj.mastertsa.cuidameapp.data.local.room.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDao {

    @Query("SELECT * FROM PatientRoom")
    fun getAllPatients(): Flow<List<PatientEntity>>

    @Query("SELECT * FROM PatientRoom WHERE id = :id")
    suspend fun getPatientById(id: Long): PatientEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPatient(patient: PatientEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPatients(list: List<PatientEntity>)

    @Update
    suspend fun updatePatient(patient: PatientEntity)

    @Query("DELETE FROM PatientRoom WHERE id = :id")
    suspend fun deletePatientById(id: Long)

    @Query("DELETE FROM PatientRoom")
    suspend fun deleteAllPatients()

    @Query("SELECT * FROM PatientRoom WHERE firstName LIKE :name || '%'")
    fun searchPatientsByName(name: String): Flow<List<PatientEntity>>

    @Query("SELECT COUNT(*) FROM PatientRoom")
    suspend fun getPatientCount(): Int
}
