package es.usj.mastertsa.cuidameapp.data.local.room.indication

import androidx.room.DatabaseView

@DatabaseView(
    "SELECT i.id, i.patientId, p.firstName || ' ' || p.lastName AS patientName, i.medicineId, m.name medicationName, i.recurrence, i.startDate, i.dosage, '' AS hour  FROM IndicationRoom AS i " +
            "INNER JOIN PatientRoom AS p ON i.patientId = p.id " +
    "INNER JOIN MedicationRoom As m ON i.medicineId = m.id"
)
data class IndicationDetailView(
    val id: Long,
    val patientId:Long,
    val patientName: String,
    val medicineId: Int,
    val medicationName: String,
    val recurrence: String,
    val startDate: String,
    val dosage: Int,
    val hour: String
)
