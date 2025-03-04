package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail

interface MedicationRepository {
    suspend fun getAllMedications(): List<Medication>
    suspend fun getMedicationById(id: Long): MedicationDetail
    suspend fun deleteMedicationById(id: Long)
    suspend fun addMedication(medication: Medication)
}