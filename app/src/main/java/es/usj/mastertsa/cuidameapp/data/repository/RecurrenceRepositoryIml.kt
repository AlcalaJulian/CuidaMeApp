package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence

class RecurrenceRepositoryIml(private val db: PatientDatabase): RecurrenceRepository {
    override suspend fun deleteRecurrences(recurrences: List<Recurrence>) {
        db.getRecurrenceDao().deleteAllRecurrences()
    }

    override suspend fun addRecurrences(recurrences: List<Recurrence>) {
        db.getRecurrenceDao().insertRecurrences(recurrences.map { it.toEntity() })
    }

    override suspend fun getDosagesForIndication(indicationId: Long): List<Recurrence> {
        return db.getRecurrenceDao().getRecurrencesForIndication(indicationId).map { it.toDomain() }
    }

    override suspend fun updateDosageCompletion(dosageId: Long, complete: Boolean) {
        db.getRecurrenceDao().updateDosageCompletion(dosageId, complete)
    }

    override suspend fun deleteDosage(dosageId: Long) {
        db.getRecurrenceDao().deleteRecurrence(dosageId)
    }
}