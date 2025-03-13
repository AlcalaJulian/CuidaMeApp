package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class GetDosagesForIndicationUseCase(
    private val recurrenceRepository: RecurrenceRepository
) {
    suspend fun execute(indicationId: Long): List<Recurrence> {
        // Calls the repository to fetch all dosages for a given indicationId
        return recurrenceRepository.getDosagesForIndication(indicationId)
    }
}
