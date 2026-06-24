package com.uls.Smartbudget.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.uls.Smartbudget.data.repository.AuthRepositoryImpl
import com.uls.Smartbudget.domain.repository.AuthRepository
import com.uls.Smartbudget.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel encargado de preparar y exponer los estados de autenticación a las vistas de Compose.
 * Hereda de ViewModel para sobrevivir a los cambios de configuración del dispositivo.
 */
class AuthViewModel(
    // Por simplicidad y orden antes de usar Hilt, instanciamos directamente el repositorio por defecto
    private val repository: AuthRepository = AuthRepositoryImpl()
) : ViewModel() {

    // Estado reactivo para el flujo de Inicio de Sesión
    private val _loginState = MutableStateFlow<Resource<Boolean>?>(null)
    val loginState: StateFlow<Resource<Boolean>?> = _loginState

    // Estado reactivo para el flujo de Registro de Usuarios
    private val _registerState = MutableStateFlow<Resource<Boolean>?>(null)
    val registerState: StateFlow<Resource<Boolean>?> = _registerState

    /**
     * Intenta iniciar sesión y actualiza el [_loginState] continuamente.
     */
    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.loginWithEmail(email, password).collect { resource ->
                _loginState.value = resource
            }
        }
    }

    /**
     * Intenta registrar un usuario y actualiza el [_registerState] continuamente.
     */
    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.registerWithEmail(name, email, password).collect { resource ->
                _registerState.value = resource
            }
        }
    }

    /**
     * Verifica de manera síncrona si hay una sesión activa de Firebase.
     */
    fun isUserLoggedIn(): Boolean {
        return repository.isUserLoggedIn()
    }

    /**
     * Cierra la sesión y limpia los estados anteriores.
     */
    fun logout() {
        repository.logout()
        _loginState.value = null
        _registerState.value = null
    }
}