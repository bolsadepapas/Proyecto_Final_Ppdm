package com.uls.Smartbudget.utils

/**
 * Clase sellada genérica para representar el estado de una operación (Firebase, red, etc.)
 */
sealed class Resource<out T> {
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Error(val message: String) : Resource<Nothing>()
}