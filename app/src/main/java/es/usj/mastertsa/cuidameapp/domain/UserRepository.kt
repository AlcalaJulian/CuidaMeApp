package es.usj.mastertsa.cuidameapp.domain

import es.usj.mastertsa.cuidameapp.domain.auth.User

interface UserRepository {
    suspend fun getUserById(id: String): User
    suspend fun registerUser(name: String, lastName: String, id: String)
}