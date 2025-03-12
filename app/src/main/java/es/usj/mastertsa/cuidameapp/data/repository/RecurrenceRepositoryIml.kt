package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence

class RecurrenceRepositoryIml(private val db: PatientDatabase): RecurrenceRepository {
    override suspend fun deleteRecurrences(recurrences: List<Recurrence>) {
        db.getRecurrenceDao().deleteAllRecurrences()
    }

    override suspend fun addRecurrences(recurrences: List<Recurrence>) {
        db.getRecurrenceDao().insertRecurrences(recurrences.map { it.toEntity() })
    }
}