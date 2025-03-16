package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toPatientDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity
import es.usj.mastertsa.cuidameapp.domain.PatientRepository
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PatientRepositoryImpl(private val db: PatientDatabase) : PatientRepository {

    private val firestore = FirebaseFirestore.getInstance()
    private val patientsCollection = firestore.collection("patients")
    private var firestoreListener: ListenerRegistration? = null

    override suspend fun getAllPatients(): List<Patient> {
        return db.getPatientDao().getAllPatients().first().map { it.toDomain() }
    }

    override suspend fun getPatientById(id: Long): PatientDetail {
        return db.getPatientDao().getPatientById(id)?.toPatientDetail()
            ?: throw NoSuchElementException("Patient with id $id not found")
    }

    override suspend fun deletePatientById(id: Long) {
        db.getPatientDao().deletePatientById(id)
        patientsCollection.document(id.toString()).delete().await()
    }

    override suspend fun addPatient(patient: Patient) {
        val entity = patient.toEntity()

        val generatedId = db.getPatientDao().insertPatient(entity)
        val entityWithId = entity.copy(id = generatedId)

        patientsCollection.document(entityWithId.id.toString()).set(entityWithId).await()
    }


    override fun syncPatientsFromFirestore() {
        firestoreListener?.remove()
        firestoreListener = patientsCollection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                return@addSnapshotListener
            }
            snapshot?.documents
                ?.mapNotNull { it.toObject(PatientEntity::class.java) }
                ?.let { patientsFromFirestore ->
                    CoroutineScope(Dispatchers.IO).launch {
                        db.getPatientDao().insertPatients(patientsFromFirestore)
                    }
                }
        }
    }
}
