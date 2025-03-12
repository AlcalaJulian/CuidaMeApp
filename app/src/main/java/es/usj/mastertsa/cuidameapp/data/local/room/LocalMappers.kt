package es.usj.mastertsa.cuidameapp.data.local.mappers

import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationDetailView
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationEntity
import es.usj.mastertsa.cuidameapp.data.local.room.indication.RecurrenceEntity
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity
import es.usj.mastertsa.cuidameapp.data.local.room.user.UserEntity
import es.usj.mastertsa.cuidameapp.domain.auth.User
import es.usj.mastertsa.cuidameapp.domain.indication.Indication
import es.usj.mastertsa.cuidameapp.domain.indication.IndicationDetail
import es.usj.mastertsa.cuidameapp.domain.indication.Recurrence
import es.usj.mastertsa.cuidameapp.domain.medicine.Medicine
import es.usj.mastertsa.cuidameapp.domain.medicine.MedicineDetail
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

fun MedicineEntity.toDomain(): Medicine {
    return Medicine(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )
}

fun Medicine.toEntity(): MedicineEntity {
    return MedicineEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )
}

fun IndicationEntity.toDomain(): Indication {
    return Indication(
        id = this.id,
//        medicationId = this.medicationId,
        patientId = this.patientId,
        medicineId = medicineId,
        recurrenceId = this.recurrence,
        startDate = this.startDate,
        //hour = startHour,
        dosage = this.dosage,
    )
}

fun Indication.toEntity(): IndicationEntity {
    return IndicationEntity(
        id = this.id,
        startDate = this.startDate,
        dosage = this.dosage,
        patientId = this.patientId,
        medicineId = medicineId,
        recurrence = recurrenceId,
        //startHour = hour
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

fun MedicineEntity.toMedicationDetail() = MedicineDetail(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )

fun MedicineDetail.toMedicationEntity() = MedicineEntity(
        id = this.id,
        name = this.name,
        description = this.description,
        administrationType = this.administrationType
    )

fun IndicationDetailView.toIndicationDetail() =  IndicationDetail(
    id = this.id,
    medicineId = this.medicineId,
    recurrenceId = this.recurrence,
    startDate = this.startDate,
    dosage = this.dosage,
    patientId = this.patientId,
    patientName = this.patientName,
    medicineName = this.medicationName,
    hour = ""
)

fun IndicationDetail.toIndicationEntity() = IndicationEntity(
    id = this.id,
    startDate = this.startDate,
    dosage = this.dosage,
    patientId = this.patientId,
    medicineId = medicineId,
    recurrence = recurrenceId
)

fun UserEntity.toDomain() = User(
    id = id,
    name = firstName,
    lastName = lastName
)


fun RecurrenceEntity.toDomain() =
     Recurrence(
        id = this.id,
        indicationId = this.indicationId,
        specificDate = this.specificDate,
        hour = this.hour,
        completed = this.completed
    )

// Extension function to map Recurrence to RecurrenceEntity
fun Recurrence.toEntity() = RecurrenceEntity(
        id = this.id,
        indicationId = this.indicationId,
        specificDate = this.specificDate,
        hour = this.hour,
        completed = this.completed
    )