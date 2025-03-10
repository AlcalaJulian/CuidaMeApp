package es.usj.mastertsa.cuidameapp.data.repository

import es.usj.mastertsa.cuidameapp.data.local.mappers.toDomain
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.local.room.user.UserEntity
import es.usj.mastertsa.cuidameapp.domain.UserRepository
import es.usj.mastertsa.cuidameapp.domain.auth.User

class UserRepositoryImpl(private val db: PatientDatabase): UserRepository {
    override suspend fun getUserById(id: String): User {
        return db.getUserDao().getUserById(id).toDomain()
    }

    override suspend fun registerUser(name: String, lastName: String, id: String) {
        db.getUserDao().insertUser(UserEntity(id, name, lastName))
    }
}