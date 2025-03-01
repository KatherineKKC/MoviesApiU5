package com.kurokawa.repository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.kurokawa.data.room.dao.UserDao
import kotlinx.coroutines.tasks.await

class LoginRepository(
    private val userDao: UserDao,
    private val auth: FirebaseAuth
) {
    // Cierra la sesión del usuario
    fun signOut() {
        auth.signOut()
    }

    suspend fun sigInFirebaseEmailAndPassword(email: String, password: String):FirebaseUser? {
       return  try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            result.user
        } catch (e: Exception) {
            null
        }
    }
}