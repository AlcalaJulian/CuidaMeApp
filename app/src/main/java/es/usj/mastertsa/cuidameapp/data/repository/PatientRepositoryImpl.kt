package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toPatientDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.PatientRepository
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class PatientRepositoryImpl(private val db: PatientDatabase): PatientRepository {

    override suspend fun getAllPatients(): List<Patient> {
        return db.getPatientDao().getAllPatients().first().map() { it.toDomain() } }

    override suspend fun getPatientById(id: Long): PatientDetail {
        return db.getPatientDao().getPatientById(id)?.toPatientDetail()
            ?: throw NoSuchElementException("Patient with id $id not found")
    }

    override suspend fun deletePatientById(id: Long) {
        return db.getPatientDao().deletePatientById(id)
    }

    override suspend fun addPatient(patient: Patient) {
        db.getPatientDao().insertPatient(patient.toEntity())

    }

}