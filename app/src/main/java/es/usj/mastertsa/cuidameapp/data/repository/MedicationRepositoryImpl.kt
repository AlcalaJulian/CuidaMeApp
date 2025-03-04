package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail

class MedicationRepositoryImpl(private val db: PatientDatabase): MedicationRepository {
    override suspend fun getAllMedications(): List<Medication> {
        TODO("Not yet implemented")
    }

    override suspend fun getMedicationById(id: Long): MedicationDetail {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMedicationById(id: Long) {
        TODO("Not yet implemented")
    }

}