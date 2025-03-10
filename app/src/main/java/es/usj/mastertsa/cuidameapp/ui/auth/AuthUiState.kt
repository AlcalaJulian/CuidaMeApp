package es.usj.mastertsa.cuidameapp.ui.auth

data class AuthUiState(
    var isLoading: Boolean = false,
    var success: Boolean = false,
    var error: String? = null
)
