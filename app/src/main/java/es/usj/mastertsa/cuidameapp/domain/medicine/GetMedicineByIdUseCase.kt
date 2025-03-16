package es.usj.mastertsa.cuidameapp.domain.medicine

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class GetMedicineByIdUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(id: Long): MedicineDetail{
        return medicationRepository.getMedicationById(id)
    }
}