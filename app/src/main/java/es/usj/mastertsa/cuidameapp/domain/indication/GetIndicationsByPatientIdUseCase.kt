package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class GetIndicationsByPatientIdUseCase (private val indicationRepository: IndicationRepository) {
    suspend fun execute(id: Long): List<IndicationDetail>{
        return indicationRepository.getIndicationsByPatientId(id)
    }
}