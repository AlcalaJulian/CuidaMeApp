package es.usj.mastertsa.cuidameapp.domain.patient

import es.usj.mastertsa.cuidameapp.domain.PatientRepository

class SyncPatientsUseCase(private val patientRepository: PatientRepository) {
    fun execute() {
        return patientRepository.syncPatientsFromFirestore()
    }
}