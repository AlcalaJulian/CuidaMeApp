package es.usj.mastertsa.cuidameapp.domain.medicine

import es.usj.mastertsa.cuidameapp.domain.MedicationRepository

class GetAllMedicinesUseCase(private val medicationRepository: MedicationRepository) {
    suspend fun execute(): List<Medicine>{
        return medicationRepository.getAllMedications()
    }
}