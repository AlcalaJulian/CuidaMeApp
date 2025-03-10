package es.usj.mastertsa.cuidameapp.domain.auth

import com.google.firebase.auth.FirebaseUser
import es.usj.mastertsa.cuidameapp.data.repository.AuthRepository

class RegisterUseCase(private val authRepository: AuthRepository) {
    suspend fun execute(email: String, password: String): FirebaseUser? {
        return authRepository.register(email, password)
    }
}