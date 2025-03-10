package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, password: String): FirebaseUser?

    suspend fun register(email: String, password: String): FirebaseUser?

    suspend fun logout()
}