package com.uls.Smartbudget

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.uls.Smartbudget.navigation.AppNavigation
import com.uls.Smartbudget.ui.auth.AuthViewModel
import com.uls.Smartbudget.ui.theme.SmartBudgetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SmartBudgetTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Instanciamos nuestro ViewModel de forma limpia
                    val authViewModel = AuthViewModel()

                    // Inicializamos el flujo completo de navegación de la app
                    AppNavigation(authViewModel = authViewModel)
                }
            }
        }
    }
}