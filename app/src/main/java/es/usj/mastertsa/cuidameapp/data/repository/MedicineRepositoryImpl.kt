
package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toMedicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MedicineRepositoryImpl(private val db: PatientDatabase) : MedicationRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val medicationsCollection = firestore.collection("medications")
    private var firestoreListener: ListenerRegistration? = null

    override suspend fun getAllMedications(): List<Medicine> {
        return db.getMedicationDao().getAllMedications().first().map { it.toDomain() }
    }

    override suspend fun getMedicationById(id: Long): MedicineDetail {
        return db.getMedicationDao().getMedicationById(id).toMedicationDetail()
    }

    override suspend fun deleteMedicationById(id: Long) {
        db.getMedicationDao().deleteMedicationById(id)
        medicationsCollection.document(id.toString()).delete().await()
    }

    override suspend fun addMedication(medication: Medicine) {
        val entity = medication.toEntity()
        val generatedId = db.getMedicationDao().insertMedication(entity)
        val entityWithId = entity.copy(id = generatedId)

        medicationsCollection.document(entityWithId.id.toString()).set(entityWithId).await()
    }

    suspend fun updateMedication(medication: Medicine) {
        val entity = medication.toEntity()
        db.getMedicationDao().updateMedication(entity)

        medicationsCollection.document(entity.id.toString()).set(entity).await()
    }

    fun syncMedicationsFromFirestore() {
        firestoreListener?.remove()

        firestoreListener = medicationsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }

            snapshot?.documents
                ?.mapNotNull { it.toObject(MedicineEntity::class.java) }
                ?.let { medicationsFromFirestore ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.getMedicationDao().deleteAllMedications()
                        db.getMedicationDao().insertMedications(medicationsFromFirestore)
                    }
                }
        }
    }
}
