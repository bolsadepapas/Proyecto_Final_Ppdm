package com.uls.Smartbudget.navigation

/**
 * Clase sellada que define las rutas de navegación de la aplicación de forma tipada y segura.
 */
sealed class Screen(val route: String) {
    object Login : Screen("login_screen")
    object Register : Screen("register_screen")
    object Dashboard : Screen("dashboard_screen")
    object MovementRegistration : Screen("movement_registration_screen")
    object History : Screen("history_screen")
    object Statistics : Screen("statistics_screen")
}