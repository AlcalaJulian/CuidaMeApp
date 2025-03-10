package es.usj.mastertsa.cuidameapp.domain.auth

import es.usj.mastertsa.cuidameapp.domain.UserRepository

class SaveLocalUserUseCase(private val userRepository: UserRepository) {
    suspend fun execute(id: String, name: String, lastName: String) {
        userRepository.registerUser(
            name = name,
            lastName = lastName,
            id = id
        )
    }
}