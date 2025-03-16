package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toIndicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationEntity
import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class IndicationRepositoryImpl(private val db: PatientDatabase): IndicationRepository {
    private val firestore = FirebaseFirestore.getInstance()
    private val indicationsCollection = firestore.collection("indications")
    private var firestoreListener: ListenerRegistration? = null

    override suspend fun getAllIndications(): List<IndicationDetail> {
        return db.getIndicationDao().getAllIndications().first().map { it.toIndicationDetail() }

    }

    override suspend fun getIndicationById(id: Long): IndicationDetail {
        return db.getIndicationDao().getIndicationDetailById(id).toIndicationDetail()

    }

    override suspend fun getIndicationsByPatientId(id: Long): List<IndicationDetail> {
        return db.getIndicationDao().getIndicationByPatientId(id).map{
            it.toIndicationDetail()
        }
    }

    override suspend fun deleteIndicationById(id: Long) {
        db.getIndicationDao().deleteIndicationById(id)
        indicationsCollection.document(id.toString()).delete().await()

    }

    override suspend fun addAnIndicationToPatient(indication: Indication): Long {
        val entity = indication.toEntity()
        val generatedId = db.getIndicationDao().insertIndication(entity)
        val entityWithId = entity.copy(id = generatedId)

        indicationsCollection.document(entityWithId.id.toString()).set(entityWithId).await()
        return generatedId
    }
    override fun syncIndicationsFromFirestore() {
        firestoreListener?.remove()

        firestoreListener = indicationsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            snapshot?.documents
                ?.mapNotNull { it.toObject(IndicationEntity::class.java) }
                ?.let { indicationsFromFirestore ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.getIndicationDao().insertIndications(indicationsFromFirestore)
                    }
                }
        }
    }


}