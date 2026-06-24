package com.uls.Smartbudget.ui.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.uls.Smartbudget.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onRegisterSuccess: () -> Unit,
    onNavigateToLogin: () -> Unit
) {
    // Variables de estado locales para los campos de registro
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Escuchamos reactivamente el estado de registro del ViewModel
    val registerState by viewModel.registerState.collectAsState()

    // Manejador de efectos secundarios para reaccionar a los cambios de estado
    LaunchedEffect(registerState) {
        when (registerState) {
            is Resource.Success -> {
                Toast.makeText(context, "¡Usuario registrado con éxito!", Toast.LENGTH_SHORT).show()
                onRegisterSuccess() // Navegar automáticamente al Dashboard
            }
            is Resource.Error -> {
                val errorMessage = (registerState as Resource.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> { /* En espera */ }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Regístrate para empezar a organizar tus presupuestos",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de Nombre Completo
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nombre completo") },
                modifier = Modifier.fillMaxWidth(),
                enabled = registerState !is Resource.Loading
            )

            // Campo de Correo Electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                enabled = registerState !is Resource.Loading
            )

            // Campo de Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = registerState !is Resource.Loading
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de Registro o Indicador de Carga
            if (registerState is Resource.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (name.isNotBlank() && email.isNotBlank() && password.isNotBlank()) {
                            if (password.length >= 6) {
                                viewModel.register(name.trim(), email.trim(), password.trim())
                            } else {
                                Toast.makeText(context, "La contraseña debe tener al menos 6 caracteres.", Toast.LENGTH_SHORT).show()
                            }
                        } else {
                            Toast.makeText(context, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Registrarme")
                }
            }

            // Enlace para volver al Login
            TextButton(
                onClick = onNavigateToLogin,
                enabled = registerState !is Resource.Loading
            ) {
                Text("¿Ya tienes cuenta? Inicia sesión")
            }
        }
    }
}