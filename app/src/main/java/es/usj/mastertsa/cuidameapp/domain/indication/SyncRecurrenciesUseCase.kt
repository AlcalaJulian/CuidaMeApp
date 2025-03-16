package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class SyncRecurrenciesUseCase(private val recurrenceRepository: RecurrenceRepository) {
    fun execute() {
        return recurrenceRepository.syncRecurrencesFromFirestore()
    }
}