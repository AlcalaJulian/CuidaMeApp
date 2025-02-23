package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail

interface IndicationRepository {
    suspend fun getAllIndications(): List<Indication>
    suspend fun getIndicationById(id: Long): IndicationDetail
    suspend fun deleteIndicationById(id: Long)
}