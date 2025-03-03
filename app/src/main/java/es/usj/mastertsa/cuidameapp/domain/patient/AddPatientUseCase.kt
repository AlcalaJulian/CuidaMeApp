package es.usj.mastertsa.cuidameapp.domain.patient

import es.usj.mastertsa.cuidameapp.domain.PatientRepository

class AddPatientUseCase(private val patientRepository: PatientRepository) {
    suspend fun execute(patient: Patient){
        return patientRepository.addPatient(patient)
    }
}