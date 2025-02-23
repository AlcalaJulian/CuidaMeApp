package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class DeleteIndicationUseCase(private val indicationRepository: IndicationRepository) {
    suspend fun execute(id: Long){
        return indicationRepository.deleteIndicationById(id)
    }
}