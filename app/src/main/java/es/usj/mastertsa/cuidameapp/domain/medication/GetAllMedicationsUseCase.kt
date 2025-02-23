package es.usj.mastertsa.cuidameapp.domain.medication

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class GetAllMedicationsUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(): List<Medication>{
        return medicationRepository.getAllMedications()
    }
}