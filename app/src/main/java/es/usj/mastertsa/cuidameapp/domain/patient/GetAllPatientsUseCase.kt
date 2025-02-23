package es.usj.mastertsa.cuidameapp.domain.patient

import es.usj.mastertsa.cuidameapp.domain.PatientRepository

class GetAllPatientsUseCase(private val patientRepository: PatientRepository) {
    suspend fun execute(): List<Patient>{
        return patientRepository.getAllPatients()
    }
}