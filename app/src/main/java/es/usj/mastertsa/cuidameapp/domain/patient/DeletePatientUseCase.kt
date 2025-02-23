package es.usj.mastertsa.cuidameapp.domain.patient

import es.usj.mastertsa.cuidameapp.domain.PatientRepository

class DeletePatientUseCase(private val patientRepository: PatientRepository) {
    suspend fun execute(id: Long){
        return patientRepository.deletePatientById(id)
    }
}