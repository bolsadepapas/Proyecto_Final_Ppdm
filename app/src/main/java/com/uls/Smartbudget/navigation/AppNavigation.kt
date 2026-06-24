package com.uls.Smartbudget.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.uls.Smartbudget.ui.auth.AuthViewModel
import com.uls.Smartbudget.ui.auth.LoginScreen
import com.uls.Smartbudget.ui.auth.RegisterScreen

@Composable
fun AppNavigation(authViewModel: AuthViewModel) {
    // 1. Creamos el controlador central de navegación de Jetpack Compose
    val navController = rememberNavController()

    // 2. Decidimos la pantalla de inicio: si el usuario está logueado va al Dashboard, si no, al Login
    // POR ESTO:
    val startDestination = if (authViewModel.isUserLoggedIn()) {
        Screen.Dashboard.route
    } else {
        Screen.Login.route // <--- Solo un 'Screen.'
    }

    // 3. Definimos el NavHost con todas las rutas disponibles en SmartBudget
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        // Ruta para la pantalla de Login
        composable(route = Screen.Login.route) {
            LoginScreen(
                viewModel = authViewModel,
                onLoginSuccess = {
                    // Al iniciar sesión con éxito, limpia el historial y va al Dashboard
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        // Ruta para la pantalla de Registro
        composable(route = Screen.Register.route) {
            RegisterScreen(
                viewModel = authViewModel,
                onRegisterSuccess = {
                    // Al registrarse con éxito, limpia el historial y va al Dashboard
                    navController.navigate(Screen.Dashboard.route) {
                        popUpTo(Screen.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.popBackStack() // Regresa de forma limpia a la pantalla anterior (Login)
                }
            )
        }

        // Ruta temporal para el Dashboard Financiero (Placeholder)
        composable(route = Screen.Dashboard.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "¡Bienvenidos al Dashboard de SmartBudget! Próximamente aquí...")
            }
        }
    }
}