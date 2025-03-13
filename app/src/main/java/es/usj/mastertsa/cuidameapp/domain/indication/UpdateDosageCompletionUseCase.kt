package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class UpdateDosageCompletionUseCase(
    private val recurrenceRepository: RecurrenceRepository
) {
    suspend fun execute(dosageId: Long, complete: Boolean) {
        // Calls the repository to update the dosage completion status
        recurrenceRepository.updateDosageCompletion(dosageId, complete)
    }
}