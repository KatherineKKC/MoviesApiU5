package com.kurokawa.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.UserEntity
import kotlinx.coroutines.tasks.await

class SignUpRepository(
    private val userDao: UserDao,
    private val auth: FirebaseAuth
) {
    private val firestore = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()

    suspend fun registerUser(
        email: String,
        password: String,
        displayName: String,
        imageUri: String
    ): Boolean {
        return try {
            // 1️⃣ Registrar usuario en Firebase Authentication
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return false
            val userId = firebaseUser.uid


           /*EJEMPLO DE SUBIR LA IMAGEN A FIREBASE STORAGE
            val imageUrl = imageUri?.let { uploadImageToFirebase(userId, it) }*/

            //  Guardar datos en Firestore
            saveUserToFirestore(userId, displayName, email)

            //  Guardar usuario en Room
            val userEntity = UserEntity(
                idFirebaseUser = userId,
                email = email,
                displayName = displayName?:"Sin displayName",
                imagePath = imageUri?:"Sin imagen"
            )
           val idNewUser = userDao.insertUser(userEntity)

            Log.e("SIGNUP-REPOSITORY", "Se ha registrado el nuevo usuario con id $idNewUser y se ha guardado en ROOM $userEntity" )
            return true
        } catch (e: Exception) {
            Log.e("SignUpRepository", "Error al registrar usuario: ${e.message}")
            return false
        }
    }



    // FUN Para guardar usuario en Firestore
    private fun saveUserToFirestore(userId: String, displayName: String, email: String) {
        val user = hashMapOf(
            "userId" to userId,
            "displayName" to displayName,
            "email" to email
        )

        firestore.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener { Log.d("Firestore", "Usuario guardado en Firestore") }
            .addOnFailureListener { e -> Log.e("Firestore", "Error al guardar usuario: ${e.message}") }
    }


}



/*
METODO PARA GUARDAR LA IMAGEN EN FIREBASE SI TENEMOS STORAGE
    private suspend fun uploadImageToFirebase(userId: String, imageUri: Uri): String {
        val storageRef = storage.reference.child("profile_images/$userId.jpg")
        return try {
            storageRef.putFile(imageUri).await()
            storageRef.downloadUrl.await().toString()
        } catch (e: Exception) {
            Log.e("SignUpRepository", "Error al subir imagen: ${e.message}")
            ""
        }
    }*/