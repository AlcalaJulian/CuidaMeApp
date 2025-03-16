package es.usj.mastertsa.cuidameapp.domain.medicine

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class SyncMedicationsUseCase(private val medicationRepository: MedicationRepository) {
    fun execute() {
        return medicationRepository.syncMedicationsFromFirestore()
    }
}