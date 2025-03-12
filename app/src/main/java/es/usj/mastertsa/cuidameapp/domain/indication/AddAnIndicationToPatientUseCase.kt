package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository

class AddAnIndicationToPatientUseCase(
    private val indicationRepository: IndicationRepository,
    private val recurrenceRepository: RecurrenceRepository
) {
    suspend fun execute(indication: Indication, recurrences: List<Recurrence>){
        indicationRepository.addAnIndicationToPatient(indication)
        recurrenceRepository.addRecurrences(recurrences)
    }
}
