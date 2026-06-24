package com.uls.Smartbudget.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.uls.Smartbudget.domain.model.User
import com.uls.Smartbudget.domain.repository.AuthRepository
import com.uls.Smartbudget.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

/**
 * Implementación real del repositorio de autenticación utilizando Firebase.
 * Usa programación asíncrona mediante Kotlin Coroutines (Flow y await).
 */
class AuthRepositoryImpl(
    private val auth: FirebaseAuth = FirebaseAuth.getInstance(),
    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
) : AuthRepository {

    override fun loginWithEmail(email: String, password: String): Flow<Resource<Boolean>> = flow {
        // 1. Emitimos el estado de carga para que la interfaz muestre un Spinner/ProgressBar
        emit(Resource.Loading)
        try {
            // 2. Intentamos iniciar sesión en Firebase Auth
            auth.signInWithEmailAndPassword(email, password).await()
            // 3. Si todo sale bien, emitimos éxito
            emit(Resource.Success(true))
        } catch (e: Exception) {
            // 4. Si falla (contraseña incorrecta, red, etc.), capturamos el error
            emit(Resource.Error(e.localizedMessage ?: "Error al iniciar sesión"))
        }
    }

    override fun registerWithEmail(name: String, email: String, password: String): Flow<Resource<Boolean>> = flow {
        emit(Resource.Loading)
        try {
            // 1. Creamos el usuario en Firebase Authentication
            val authResult = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = authResult.user?.uid ?: throw Exception("No se pudo obtener el identificador único del usuario.")

            // 2. Creamos el objeto con la información básica utilizando nuestro modelo de dominio
            val newUser = User(
                uid = uid,
                name = name,
                email = email,
                createdAt = System.currentTimeMillis()
            )

            // 3. Guardamos el objeto de forma persistente en la colección "users" de Firestore
            firestore.collection("users").document(uid).set(newUser).await()

            emit(Resource.Success(true))
        } catch (e: Exception) {
            emit(Resource.Error(e.localizedMessage ?: "Error durante el registro de usuario"))
        }
    }

    override fun isUserLoggedIn(): Boolean {
        // Verifica si existe una sesión activa en el dispositivo
        return auth.currentUser != null
    }

    override fun logout() {
        // Cierra la sesión del usuario actual
        auth.signOut()
    }
}