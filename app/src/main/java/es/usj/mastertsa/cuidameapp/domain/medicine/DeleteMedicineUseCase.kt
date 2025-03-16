package es.usj.mastertsa.cuidameapp.domain.medicine

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class DeleteMedicineUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(id: Long){
        return medicationRepository.deleteMedicationById(id)
    }
}