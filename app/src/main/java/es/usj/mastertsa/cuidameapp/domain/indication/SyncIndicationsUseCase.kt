package es.usj.mastertsa.cuidameapp.domain.indication

import es.usj.mastertsa.cuidameapp.domain.IndicationRepository

class SyncIndicationsUseCase(private val indicationRepository: IndicationRepository) {
    fun execute() {
        return indicationRepository.syncIndicationsFromFirestore()
    }
}