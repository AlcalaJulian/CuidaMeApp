package es.usj.mastertsa.cuidameapp.ui.auth

import android.util.Patterns
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import es.usj.mastertsa.cuidameapp.domain.share.Util
import es.usj.mastertsa.cuidameapp.ui.shared.ErrorText
import es.usj.mastertsa.cuidameapp.ui.shared.LoadingIndicator


@Composable
fun LoginScreen(authViewModel: AuthViewModel, navigateBack: () -> Boolean) {
    var email by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isLogin by remember { mutableStateOf(true) }

    var emailError by remember { mutableStateOf<String?>(null) }
    var passwordError by remember { mutableStateOf<String?>(null) }
    var nameError by remember { mutableStateOf<String?>(null) }
    var lastNameError by remember { mutableStateOf<String?>(null) }

    val authUiState = authViewModel.uiState

    when {
        authUiState.isLoading -> {
            LoadingIndicator()
        }
        authUiState.error != null -> {
            ErrorText(authUiState.error.toString())
        }
        else -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .background(MaterialTheme.colorScheme.background)
            ) {
                Text(
                    text = if(isLogin) "Login" else "Regisrer",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier
                        .padding(bottom = 24.dp)
                )

                if (!isLogin){
                    OutlinedTextField(
                        value = name,
                        onValueChange = { name = it },
                        isError = Util.validateName(name),
                        label = { Text("Primer nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Person Icon")
                        }
                    )
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
                        isError = Util.validateName(lastName),
                        label = { Text("Secundo nombre") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = "Person Icon")
                        }
                    )
                }

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email),
                    label = { Text("Email Address") },
                    isError = Util.validateEmail(email),
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Email, contentDescription = "Email Icon")
                    }
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    leadingIcon = {
                        Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                    },
                    visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                    trailingIcon = {
                        IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                            Icon(
                                imageVector = if (isPasswordVisible) Icons.Default.Search else Icons.Default.Search,
                                contentDescription = "Toggle password visibility"
                            )
                        }
                    }
                )

                if (!isLogin) {
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = confirmPassword,
                        onValueChange = { confirmPassword = it },
                        label = { Text("Confirm password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        leadingIcon = {
                            Icon(Icons.Default.Lock, contentDescription = "Password Icon")
                        },
                        visualTransformation = if (isConfirmPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            IconButton(onClick = {
                                isConfirmPasswordVisible = !isConfirmPasswordVisible
                            }) {
                                Icon(
                                    imageVector = if (isConfirmPasswordVisible) Icons.Default.Search else Icons.Default.Search,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (isLogin){
                            authViewModel.login(email, password)
                        }else{
                            authViewModel.register(email, password, name, lastName)
                        }},
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(text = "Enviar", style = MaterialTheme.typography.bodySmall)
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (authUiState.error != null) {
//                    Text(
//                        text = authUiState.error,
//                        style = MaterialTheme.typography.bodySmall,
//                    )
//                    Text(
//                        text = authUiState.error,
//                        color = MaterialTheme.colorScheme.error,
//                        style = MaterialTheme.typography.bodySmall,
//                        modifier = Modifier.padding(top = 8.dp)
//                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                TextButton(onClick = {
                    name = ""
                    lastName = ""
                    email = ""
                    password = ""
                    confirmPassword = ""

                    isLogin = !isLogin
                }) {
                    Text(
                        if(isLogin) "Don't have an account? Sign up" else "I have an account",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}