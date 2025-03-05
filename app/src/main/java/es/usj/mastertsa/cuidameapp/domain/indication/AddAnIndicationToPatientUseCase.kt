package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class AddAnIndicationToPatientUseCase(private val indicationRepository: IndicationRepository) {
    suspend fun execute(indication: Indication){
        return indicationRepository.addAnIndicationToPatient(indication)
    }
}
