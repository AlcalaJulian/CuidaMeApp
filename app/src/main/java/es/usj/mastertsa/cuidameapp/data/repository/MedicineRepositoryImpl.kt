package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toMedicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail
import kotlinx.coroutines.flow.first

class MedicineRepositoryImpl(private val db: PatientDatabase): MedicationRepository {
    override suspend fun getAllMedications(): List<Medicine> {
        return db.getMedicationDao().getAllMedications().first().map { it.toDomain() }
    }

    override suspend fun getMedicationById(id: Long): MedicineDetail {
        return db.getMedicationDao().getMedicationById(id).toMedicationDetail()
    }

    override suspend fun deleteMedicationById(id: Long) {
        db.getMedicationDao().deleteMedicationById(id)
    }

    override suspend fun addMedication(medication: Medicine) {
        db.getMedicationDao().insertMedication(medication.toEntity())
    }

}