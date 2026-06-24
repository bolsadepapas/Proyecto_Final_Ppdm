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
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit
) {
    // Variables de estado locales de Compose para los campos de texto
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    // Escuchamos de forma reactiva el estado del Login de nuestro ViewModel
    val loginState by viewModel.loginState.collectAsState()

    // Manejador del flujo de estados (Efecto secundario)
    LaunchedEffect(loginState) {
        when (loginState) {
            is Resource.Success -> {
                onLoginSuccess() // Navegar al Dashboard financiero
            }
            is Resource.Error -> {
                val errorMessage = (loginState as Resource.Error).message
                Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
            }
            else -> { /* No hacer nada o en espera */ }
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
                text = "SmartBudget",
                style = MaterialTheme.typography.headlineLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Controla tus finanzas desde hoy",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de texto para Correo Electrónico
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo electrónico") },
                modifier = Modifier.fillMaxWidth(),
                enabled = loginState !is Resource.Loading
            )

            // Campo de texto para Contraseña
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = loginState !is Resource.Loading
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Botón de Iniciar Sesión o Indicador de Carga
            if (loginState is Resource.Loading) {
                CircularProgressIndicator()
            } else {
                Button(
                    onClick = {
                        if (email.isNotBlank() && password.isNotBlank()) {
                            viewModel.login(email.trim(), password.trim())
                        } else {
                            Toast.makeText(context, "Por favor, llena todos los campos.", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Iniciar Sesión")
                }
            }

            // Enlace para ir a la pantalla de Registro
            TextButton(
                onClick = onNavigateToRegister,
                enabled = loginState !is Resource.Loading
            ) {
                Text("¿No tienes cuenta? Regístrate aquí")
            }
        }
    }
}