package com.kurokawa.repository

import android.net.Uri
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.UserEntity
import kotlinx.coroutines.tasks.await

class SignUpRepository(
    private val userDao: UserDao,
    private val auth: FirebaseAuth
) {
    private val firestore = FirebaseFirestore.getInstance()

    suspend fun registerUser(
        email: String,
        password: String,
        displayName: String,
        imageUri: Uri?
    ): Boolean {
        return try {
            // CREA EL USUARIO EN FIREBASE
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val firebaseUser = result.user ?: return false
            val userId = firebaseUser.uid

            // val imageUrl = imageUri?.let { uploadImageToFirebase(userId, it) } ?: ""

            //GUARDAMOS EN FIRESTORE LOS DATOS
            saveUserToFirestore(userId, displayName, email, imageUri.toString())

            //GUARDA EL USUARIO EN ROOM
            val userEntity = UserEntity(
                idFirebaseUser = userId,
                email = email,
                displayName = displayName,
                imagePath = imageUri.toString()
            )
            userDao.insertUser(userEntity)

            Log.e("SIGNUP-REPOSITORY", "Usuario registrado y guardado en ROOM: $userEntity")
            return true
        } catch (e: Exception) {
            Log.e("SignUpRepository", "Error al registrar usuario: ${e.message}")
            return false
        }
    }


    // GUARDAR USUARIO EN FIREBASE
    private fun saveUserToFirestore(
        userId: String,
        displayName: String,
        email: String,
        imagePath: String
    ) {
        val user = hashMapOf(
            "userId" to userId,
            "displayName" to displayName,
            "email" to email,
            "imagePath" to imagePath
        )

        firestore.collection("users").document(userId)
            .set(user)
            .addOnSuccessListener { Log.d("Firestore", "Usuario guardado en Firestore") }
            .addOnFailureListener { e ->
                Log.e(
                    "Firestore",
                    "Error al guardar usuario: ${e.message}"
                )
            }
    }
}
/*EJEMPLO DE COMO GUARDAR UNA IMAGEN EN FIRABSE STORAGE(DE PAGO)
 private suspend fun uploadImageToFirebase(userId: String, imageUri: Uri): String {
       val storageRef = storage.reference.child("profile_images/$userId.jpg")
       return try {
           storageRef.putFile(imageUri).await()
           storageRef.downloadUrl.await().toString()
       } catch (e: Exception) {
           Log.e("SignUpRepository", "Error al subir imagen: ${e.message}")
           ""
       }
   }
*/