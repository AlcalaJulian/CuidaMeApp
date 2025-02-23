package es.usj.mastertsa.cuidameapp.data.local.room

import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail
import kotlinx.coroutines.flow.Flow

interface PatientDatabaseDatasource {

    suspend fun savePatients(patients: List<Patient>)

    fun getPatients(): Flow<List<Patient>>

    suspend fun getPatientById(patientId: Long): PatientDetail?

    suspend fun savePatientById(patientDetail: PatientDetail)
}