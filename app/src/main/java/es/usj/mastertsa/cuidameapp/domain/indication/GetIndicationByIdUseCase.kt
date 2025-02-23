package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class GetIndicationByIdUseCase(private val indicationRepository: IndicationRepository) {
    suspend fun execute(id: Long): IndicationDetail{
        return indicationRepository.getIndicationById(id)
    }
}