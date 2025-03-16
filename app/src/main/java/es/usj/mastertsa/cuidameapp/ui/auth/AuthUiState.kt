package es.usj.mastertsa.cuidameapp.ui.auth

import com.google.firebase.auth.FirebaseUser

data class AuthUiState(
    var isLoading: Boolean = false,
    var success: Boolean = false,
    var error: String? = null,
    var user: FirebaseUser? = null
)
