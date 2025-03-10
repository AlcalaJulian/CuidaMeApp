package es.usj.mastertsa.cuidameapp.domain.auth

import es.usj.mastertsa.cuidameapp.data.repository.AuthRepository

class LogoutUseCase(private val authRepository: AuthRepository) {
    suspend fun execute() {
        authRepository.logout()
    }
}