package es.usj.mastertsa.cuidameapp.ui.auth

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import es.usj.mastertsa.cuidameapp.data.local.room.PatientDatabase
import es.usj.mastertsa.cuidameapp.data.remote.AuthFirebaseDataSource
import es.usj.mastertsa.cuidameapp.data.repository.AuthRepositoryImpl
import es.usj.mastertsa.cuidameapp.data.repository.UserRepositoryImpl
import es.usj.mastertsa.cuidameapp.domain.auth.LoginUseCase
import es.usj.mastertsa.cuidameapp.domain.auth.LogoutUseCase
import es.usj.mastertsa.cuidameapp.domain.auth.RegisterUseCase
import es.usj.mastertsa.cuidameapp.domain.auth.SaveLocalUserUseCase
import kotlinx.coroutines.launch

class AuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
    private val logoutUseCase: LogoutUseCase,
    private val saveLocalUserUseCase: SaveLocalUserUseCase
) : ViewModel() {

//    var user: FirebaseUser? = null
//        private set

    var uiState by mutableStateOf(AuthUiState())
        private set

    fun login(email: String, password: String) {
        viewModelScope.launch {
            try {
                uiState = uiState.copy(isLoading = true)
                val loggedInUser = loginUseCase.execute(email, password)
                if (loggedInUser != null) {
                    uiState = uiState.copy(user = loggedInUser, success = true)
                }

            }catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, error = ex.message)
            }

        }
    }

    fun register(email: String, password: String, name: String, lastName: String) {
        viewModelScope.launch {

            try {
                uiState = uiState.copy(isLoading = true)
                val registeredUser = registerUseCase.execute(email, password)

                if (registeredUser != null) {
                    saveLocalUserUseCase.execute(registeredUser.uid, name, lastName)
                }
                uiState = uiState.copy(success = true, user = registeredUser)

            }catch (ex: Exception){
                uiState = uiState.copy(isLoading = false, error = ex.message)
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            logoutUseCase.execute()
            uiState = uiState.copy(user = null)
        }
    }

    companion object{
        fun factory(context: Context):ViewModelProvider.Factory = object :ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val loginUseCase = LoginUseCase(AuthRepositoryImpl(AuthFirebaseDataSource(FirebaseAuth.getInstance())))
                val registerUseCase = RegisterUseCase(AuthRepositoryImpl(AuthFirebaseDataSource(FirebaseAuth.getInstance())))
                val logoutUseCase = LogoutUseCase(AuthRepositoryImpl(AuthFirebaseDataSource(FirebaseAuth.getInstance())))

                val userRepository = UserRepositoryImpl(PatientDatabase.provideDatabase(context))
                val userLocalUserUseCase = SaveLocalUserUseCase(userRepository)
                return AuthViewModel(loginUseCase, registerUseCase, logoutUseCase, userLocalUserUseCase) as T
            }
        }
    }
}