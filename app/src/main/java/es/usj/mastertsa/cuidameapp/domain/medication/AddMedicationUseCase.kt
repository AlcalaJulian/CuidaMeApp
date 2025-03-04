package es.usj.mastertsa.cuidameapp.domain.medication

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class AddMedicationUseCase( private val medicationRepository: MedicationRepository) {
    suspend fun execute(medication: Medication){
        return medicationRepository.addMedication(medication)
    }
}