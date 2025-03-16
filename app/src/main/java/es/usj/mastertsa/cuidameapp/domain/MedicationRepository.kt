package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail

interface MedicationRepository {
    suspend fun getAllMedications(): List<Medicine>
    suspend fun getMedicationById(id: Long): MedicineDetail
    suspend fun deleteMedicationById(id: Long)
    suspend fun addMedication(medication: Medicine)
    fun syncMedicationsFromFirestore()
}