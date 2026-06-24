package com.uls.Smartbudget.domain.repository

import com.uls.Smartbudget.utils.Resource
import kotlinx.coroutines.flow.Flow

/**
 * Interfaz que define las operaciones de autenticación y gestión de usuarios.
 * Se utiliza Flow para transmitir en tiempo real los estados (Loading, Success, Error).
 */
interface AuthRepository {
    fun loginWithEmail(email: String, password: String): Flow<Resource<Boolean>>
    fun registerWithEmail(name: String, email: String, password: String): Flow<Resource<Boolean>>
    fun isUserLoggedIn(): Boolean
    fun logout()
}