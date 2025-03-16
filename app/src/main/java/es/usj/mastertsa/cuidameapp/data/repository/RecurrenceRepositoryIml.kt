package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.indication.RecurrenceEntity
import es.usj.mastertsa.cuidameapp.domain.RecurrenceRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Dosage
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RecurrenceRepositoryIml(private val db: PatientDatabase): RecurrenceRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val recurrencesCollection = firestore.collection("recurrences")
    private var firestoreListener: ListenerRegistration? = null
    override suspend fun deleteRecurrences(recurrences: List<Recurrence>) {
        db.getRecurrenceDao().deleteAllRecurrences()
        recurrences.forEach {
            recurrencesCollection.document(it.id.toString()).delete().await()
        }
    }

    override suspend fun addRecurrences(recurrences: List<Recurrence>) {
        val entities = recurrences.map { it.toEntity() }
        db.getRecurrenceDao().insertRecurrences(entities)
        entities.forEach {
            recurrencesCollection.document(it.id.toString()).set(it).await()
        }    }

    override suspend fun getDosagesForIndication(indicationId: Long): List<Recurrence> {
        return db.getRecurrenceDao().getRecurrencesForIndication(indicationId).map { it.toDomain() }
    }

    override suspend fun updateDosageCompletion(dosageId: Long, complete: Boolean) {
        db.getRecurrenceDao().updateDosageCompletion(dosageId, complete)
        recurrencesCollection.document(dosageId.toString()).update("completed", complete).await()

    }

    override suspend fun deleteDosage(dosageId: Long) {
        db.getRecurrenceDao().deleteRecurrence(dosageId)
        recurrencesCollection.document(dosageId.toString()).delete().await()

    }
    override fun syncRecurrencesFromFirestore() {
        firestoreListener?.remove()

        firestoreListener = recurrencesCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            snapshot?.documents
                ?.mapNotNull { it.toObject(RecurrenceEntity::class.java) }
                ?.let { recurrencesFromFirestore ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.getRecurrenceDao().deleteAllRecurrences()
                        db.getRecurrenceDao().insertRecurrences(recurrencesFromFirestore)
                    }
                }
        }
    }
}