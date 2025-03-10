package es.usj.mastertsa.cuidameapp.data.repository

import com.google.firebase.auth.FirebaseUser
import es.usj.mastertsa.cuidameapp.data.remote.AuthRemoteDataSource

class AuthRepositoryImpl(private val authRemoteDataSource: AuthRemoteDataSource): AuthRepository {
        override suspend fun login(email: String, password: String): FirebaseUser? {
            return authRemoteDataSource.login(email, password)
        }

        override suspend fun register(email: String, password: String): FirebaseUser? {
            return authRemoteDataSource.register(email, password)
        }

        override suspend fun logout() {
            authRemoteDataSource.logout()
        }
}