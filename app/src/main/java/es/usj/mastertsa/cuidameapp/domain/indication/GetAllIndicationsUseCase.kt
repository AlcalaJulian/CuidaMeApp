package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class GetAllIndicationsUseCase(private val indicationRepository: IndicationRepository) {
    suspend fun execute(): List<Indication>{
        return indicationRepository.getAllIndications()
    }
}