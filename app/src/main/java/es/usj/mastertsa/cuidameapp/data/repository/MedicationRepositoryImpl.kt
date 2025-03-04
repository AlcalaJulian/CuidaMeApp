package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toMedicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class MedicationRepositoryImpl(private val db: PatientDatabase): MedicationRepository {
    override suspend fun getAllMedications(): List<Medication> {
        return db.getMedicationDao().getAllMedications().first().map { it.toDomain() }
    }

    override suspend fun getMedicationById(id: Long): MedicationDetail {
        return db.getMedicationDao().getMedicationById(id).toMedicationDetail()
    }

    override suspend fun deleteMedicationById(id: Long) {
        db.getMedicationDao().deleteMedicationById(id)
    }

    override suspend fun addMedication(medication: Medication) {
        db.getMedicationDao().insertMedication(medication.toEntity())
    }

}