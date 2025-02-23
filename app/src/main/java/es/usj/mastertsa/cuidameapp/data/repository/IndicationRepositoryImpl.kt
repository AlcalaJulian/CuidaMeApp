package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.PatientRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail

class IndicationRepositoryImpl(private val db: PatientDatabase): IndicationRepository, MedicationRepository, PatientRepository {
    override suspend fun getAllIndications(): List<Indication> {
        TODO("Not yet implemented")
    }

    override suspend fun getIndicationById(id: Long): IndicationDetail {
        TODO("Not yet implemented")
    }

    override suspend fun deleteIndicationById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllMedications(): List<Medication> {
        TODO("Not yet implemented")
    }

    override suspend fun getMedicationById(id: Long): MedicationDetail {
        TODO("Not yet implemented")
    }

    override suspend fun deleteMedicationById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllPatients(): List<Patient> {
        TODO("Not yet implemented")
    }

    override suspend fun getPatientById(id: Long): PatientDetail {
        TODO("Not yet implemented")
    }

    override suspend fun deletePatientById(id: Long) {
        TODO("Not yet implemented")
    }
}