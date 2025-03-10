package es.usj.mastertsa.cuidameapp.data.local.mappers

import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationDetailView
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationEntity
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity
import es.usj.mastertsa.cuidameapp.data.local.room.medication.MedicationEntity
import es.usj.mastertsa.cuidameapp.data.local.room.user.UserEntity
import es.usj.mastertsa.cuidameapp.domain.auth.User
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.medication.Medication
import es.usj.mastertsa.cuidameapp.domain.medication.MedicationDetail
import es.usj.mastertsa.cuidameapp.domain.patient.Patient
import es.usj.mastertsa.cuidameapp.domain.patient.PatientDetail


fun PatientEntity.toDomain(): Patient {
    return Patient(
        id = this.id,
        identification = this.identification,
        identificationType = this.identificationType,
        firstName = this.firstName,
        lastName = this.lastName,
        birthDate = this.birthDate,
        emergencyContact = this.emergencyContact
    )
}

fun Patient.toEntity(): PatientEntity {
    return PatientEntity(
        id = this.id,
        identification = this.identification,
        identificationType = this.identificationType,
        firstName = this.firstName,
        lastName = this.lastName,
        birthDate = this.birthDate,
        emergencyContact = this.emergencyContact
    )
}

fun MedicationEntity.toDomain(): Medication {
    return Medication(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )
}

fun Medication.toEntity(): MedicationEntity {
    return MedicationEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )
}

fun IndicationEntity.toDomain(): Indication {
    return Indication(
        id = this.id,
        medicationId = this.medicationId,
        recurrenceId = this.recurrenceId,
        startDate = this.startDate,
        dosage = this.dosage,
        patientId = this.patientId
    )
}

fun Indication.toEntity(): IndicationEntity {
    return IndicationEntity(
        id = this.id,
        medicationId = this.medicationId,
        recurrenceId = this.recurrenceId,
        startDate = this.startDate,
        dosage = this.dosage,
        patientId = this.patientId
    )
}

//
fun PatientEntity.toPatientDetail() = PatientDetail(
        id = this.id,
        identification = this.identification,
        identificationType = this.identificationType,
        firstName = this.firstName,
        lastName = this.lastName,
        birthDate = this.birthDate,
        emergencyContact = this.emergencyContact
    )

fun PatientDetail.toPatientEntity() = PatientEntity(
        id = this.id,
        identification = this.identification,
        identificationType = this.identificationType,
        firstName = this.firstName,
        lastName = this.lastName,
        birthDate = this.birthDate,
        emergencyContact = this.emergencyContact
    )

fun MedicationEntity.toMedicationDetail() = MedicationDetail(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )

fun MedicationDetail.toMedicationEntity() = MedicationEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )

fun IndicationDetailView.toIndicationDetail() =  IndicationDetail(
        id = this.id,
         medicationId = this.medicationId,
        recurrenceId = this.recurrenceId,
        startDate = this.startDate,
        dosage = this.dosage,
        patientId = this.patientId,
        patientName = this.patientName,
        medicationName = this.medicationName
    )

fun IndicationDetail.toIndicationEntity() = IndicationEntity(
        id = this.id,
        medicationId = this.medicationId,
        recurrenceId = this.recurrenceId,
        startDate = this.startDate,
        dosage = this.dosage,
        patientId = this.patientId
    )

fun UserEntity.toDomain() = User(
    id = id,
    name = firstName,
    lastName = lastName
)