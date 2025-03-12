package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence
import es.usj.mastertsa.cuidameapp.domain.patient.Patient

interface RecurrenceRepository {
    suspend fun deleteRecurrences(recurrences: List<Recurrence>)
    suspend fun addRecurrences(recurrences: List<Recurrence>)
}