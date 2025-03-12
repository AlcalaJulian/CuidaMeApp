package es.usj.mastertsa.cuidameapp.domain.medicine

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class AddMedicineUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(medication: Medicine){
        return medicationRepository.addMedication(medication)
    }
}