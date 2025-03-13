package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toEntity
import es.usj.mastertsa.cuidameapp.data.local.mappers.toIndicationDetail
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.domain.IndicationRepository
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import kotlinx.coroutines.flow.first

class IndicationRepositoryImpl(private val db: PatientDatabase): IndicationRepository {
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
    }

    override suspend fun addAnIndicationToPatient(indication: Indication): Long {
        return db.getIndicationDao().insertIndication(indication.toEntity())
    }


}