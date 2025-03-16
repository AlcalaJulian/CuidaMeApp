package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class DeleteDosageUseCase(
    private val repository: RecurrenceRepository
) {
    suspend fun execute(dosageId: Long) {
        // Calls the repository to delete the dosage
        repository.deleteDosage(dosageId)
    }
}
