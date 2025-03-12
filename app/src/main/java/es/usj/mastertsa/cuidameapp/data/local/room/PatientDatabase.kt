package es.usj.mastertsa.cuidameapp.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationDao
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationDetailView
import es.usj.mastertsa.cuidameapp.data.local.room.indication.IndicationEntity
import es.usj.mastertsa.cuidameapp.data.local.room.indication.RecurrenceDao
import es.usj.mastertsa.cuidameapp.data.local.room.indication.RecurrenceEntity
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineDao
import es.usj.mastertsa.cuidameapp.data.local.room.medicine.MedicineEntity
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientDao
import es.usj.mastertsa.cuidameapp.data.local.room.patient.PatientEntity
import es.usj.mastertsa.cuidameapp.data.local.room.user.UserDao
import es.usj.mastertsa.cuidameapp.data.local.room.user.UserEntity

@Database(entities = [PatientEntity::class,MedicineEntity::class,IndicationEntity::class, UserEntity::class, RecurrenceEntity::class], views = [IndicationDetailView::class], exportSchema = false, version = 1)
abstract class PatientDatabase : RoomDatabase() {

    abstract fun getPatientDao(): PatientDao
    abstract fun getMedicationDao(): MedicineDao
    abstract fun getIndicationDao(): IndicationDao
    abstract fun getUserDao(): UserDao
    abstract fun getRecurrenceDao(): RecurrenceDao

    companion object {

        private var db: PatientDatabase? = null

        fun provideDatabase(application: Context): PatientDatabase {
            return db ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application,
                    PatientDatabase::class.java, "PatientDb.db"
                ).build()
                db = instance
                instance
            }
        }
    }
}