package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail

interface PatientRepository {
    suspend fun getAllPatients(): List<Patient>
    suspend fun getPatientById(id: Long): PatientDetail
    suspend fun deletePatientById(id: Long)
}