package es.usj.mastertsa.cuidameapp.domain.medication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class DeleteMedicationUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(id: Long){
        return medicationRepository.deleteMedicationById(id)
    }
}