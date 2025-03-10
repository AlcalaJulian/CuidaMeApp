package es.usj.mastertsa.cuidameapp.domain.auth

import es.usj.mastertsa.cuidameapp.domain.UserRepository

class GetUserUseCase(private val userRepository: UserRepository) {
suspend fun execute(id: String): User{
   return userRepository.getUserById(id)
}
}