package es.usj.mastertsa.cuidameapp.data.remote

import com.google.firebase.auth.FirebaseUser

interface AuthRemoteDataSource {
    suspend fun login(email: String, password: String): FirebaseUser?
    suspend fun register(email: String, password: String): FirebaseUser?
    suspend fun logout()
}