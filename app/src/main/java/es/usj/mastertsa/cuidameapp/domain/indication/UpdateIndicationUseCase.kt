package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class UpdateIndicationUseCase(
    private val indicationRepository: IndicationRepository
) {
    suspend fun execute(indication: Indication) {
        // Calls the repository to update the indication
        //indicationRepository(indication)
    }
}