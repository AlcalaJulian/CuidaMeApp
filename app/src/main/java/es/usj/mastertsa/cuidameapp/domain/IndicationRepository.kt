package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

interface IndicationRepository {
    suspend fun getAllIndications(): List<IndicationDetail>
    suspend fun getIndicationById(id: Long): IndicationDetail
    suspend fun getIndicationsByPatientId(id: Long): List<IndicationDetail>
    suspend fun deleteIndicationById(id: Long)
    suspend fun addAnIndicationToPatient(indication: Indication)
}