package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class SyncRecurrenceUseCase(private val recurrenceRepository: RecurrenceRepository) {
    fun execute() {
        return recurrenceRepository.syncRecurrencesFromFirestore()
    }
}