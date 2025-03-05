package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toIndicationDetail
import es.usj.mastertsa.cuidameapp.data.local.mappers.toMedicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.MedicationRepository
import es.usj.mastertsa.cuidameapp.domain.PatientRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail
import kotlinx.coroutines.flow.first

class IndicationRepositoryImpl(private val db: PatientDatabase): IndicationRepository {
    override suspend fun getAllIndications(): List<Indication> {
        return db.getIndicationDao().getAllIndications().first().map { it.toDomain() }

    }

    override suspend fun getIndicationById(id: Long): IndicationDetail {
        return db.getIndicationDao().getIndicationById(id).toIndicationDetail()

    }

    override suspend fun getIndicationsByPatientId(id: Long): List<IndicationDetail> {
        val indicationEntities = db.getIndicationDao().getIndicationByPatientId(id).map{
            it.toIndicationDetail()
        }
        return indicationEntities
    }


    override suspend fun deleteIndicationById(id: Long) {
        db.getIndicationDao().deleteIndicationById(id)
    }

    override suspend fun addAnIndicationToPatient(indication: Indication) {
        db.getIndicationDao().insertIndication(indication.toEntity())
    }


}