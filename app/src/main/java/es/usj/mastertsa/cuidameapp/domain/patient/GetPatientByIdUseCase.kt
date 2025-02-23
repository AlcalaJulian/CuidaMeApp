package es.usj.mastertsa.cuidameapp.domain.patient

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.PatientRepository

class GetPatientByIdUseCase(private val patientRepository: PatientRepository) {
    suspend fun execute(id: Long): PatientDetail{
        return patientRepository.getPatientById(id)
    }
}