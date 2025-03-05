package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Delete
import kotlinx.coroutines.flow.Flow

@Dao
interface IndicationDao {

    @Query("SELECT * FROM IndicationRoom")
    fun getAllIndications(): Flow<List<IndicationEntity>>

    @Query("SELECT * FROM IndicationRoom WHERE id = :id")
    suspend fun getIndicationById(id: Long): IndicationEntity

    @Query("SELECT * FROM IndicationRoom WHERE patientId = :id")
    suspend fun getIndicationByPatientId(id: Long): List<IndicationEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIndication(indication: IndicationEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIndications(list: List<IndicationEntity>)

    @Update
    suspend fun updateIndication(indication: IndicationEntity)

    @Query("DELETE FROM IndicationRoom WHERE id = :id")
    suspend fun deleteIndicationById(id: Long)

    @Query("DELETE FROM IndicationRoom")
    suspend fun deleteAllIndications()

    @Query("SELECT * FROM IndicationRoom WHERE medicationId = :medicationId")
    fun getIndicationsByMedicationId(medicationId: Long): Flow<List<IndicationEntity>>

    @Query("SELECT COUNT(*) FROM IndicationRoom")
    suspend fun getIndicationsCount(): Int
}
