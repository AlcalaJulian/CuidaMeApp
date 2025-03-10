package es.usj.mastertsa.cuidameapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await

class AuthFirebaseDataSource(private val firebaseAuth: FirebaseAuth): AuthRemoteDataSource {
    override suspend fun login(email: String, password: String): FirebaseUser? {
        return try {
            firebaseAuth.signInWithEmailAndPassword(email, password).await().user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun register(email: String, password: String): FirebaseUser? {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(email, password).await().user
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun logout() {
        firebaseAuth.signOut()
    }

}