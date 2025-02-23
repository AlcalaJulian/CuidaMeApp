package es.usj.mastertsa.cuidameapp.domain.medication

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class GetMedicationByIdUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(id: Long): MedicationDetail{
        return medicationRepository.getMedicationById(id)
    }
}