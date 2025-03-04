package com.kurokawa.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.UserEntity
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class LoginRepository(private val userDao: UserDao, private val auth: FirebaseAuth) {
    // Cierra la sesión del usuario
    fun signOut() {
        auth.signOut()
    }

    //INICIA SESION EN FIREBASE Y GUARDA LOS DATOS EN ROOM DE NO ESTAR REGISTRADO EN LA DB LOCAL
    private var idUSer = ""
    suspend fun sigInFirebaseEmailAndPassword(email: String, password: String): UserEntity? {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return null
            idUSer = firebaseUser.uid

            // Verificar si el usuario ya existe en Room
            var userEntity = userDao.getUserByFirebaseId(idUSer)
            if (userEntity != null) {
                Log.e("AUTH", "Usuario ya existe en Room: $userEntity")
            } else {
                // Si no existe, crearlo y almacenarlo en Room
                val displayName = firebaseUser.displayName ?: "Sin nombre"
                val imagePath = firebaseUser.photoUrl?.toString() ?: ""
                userEntity = UserEntity(
                    idFirebaseUser = idUSer,
                    email = email,
                    displayName = displayName,
                    imagePath = imagePath
                )
                userDao.insertUser(userEntity)
                Log.e("AUTH", "Usuario almacenado en Room: $userEntity")
            }
            userEntity

        } catch (e: Exception) {
            Log.e("AUTH", "Error en signInFirebaseEmailAndPassword: ${e.message}")
            null
        }
    }


    suspend fun getUserById(): UserEntity? {
        return userDao.getUserByFirebaseId(idUSer)
    }


    suspend fun deleteUserFromRoom(userFirebaseId: String) {
        try {
            userDao.deleteUserByFirebaseId(userFirebaseId)
            val auth = FirebaseAuth.getInstance().currentUser
            if (auth != null) {
                auth.delete().addOnCompleteListener { delete ->
                    if (delete.isSuccessful) {
                        Log.e("AUTH", "Usuario eliminado de Room y de Firebase: $userFirebaseId")
                    }
                }
            } else {
                Log.e("AUTH", "Usuario no ha sido correctamente eliminado: $userFirebaseId")
            }
        } catch (e: Exception) {
            Log.e("AUTH", "Error al eliminar usuario de Room: ${e.message}")
        }
    }

    //INGRESA A LA APP COMO ANONIMO /INVITADO
    suspend fun signInLikeAnonymous(): String? = suspendCancellableCoroutine { continuation ->
        val auth = FirebaseAuth.getInstance()
        auth.signInAnonymously()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val idAnonymous = auth.currentUser?.uid
                    Log.e("AUTH", "Usuario anónimo creado con UID: $idAnonymous")
                    continuation.resume(idAnonymous) // Devuelve el UID
                } else {
                    Log.e("AUTH", "Error en autenticación anónima", task.exception)
                    continuation.resumeWithException(
                        task.exception ?: Exception("Error desconocido")
                    )
                }
            }
    }


}