package com.kurokawa.repository

import com.kurokawa.data.sharedPreferences.entities.UserEntity
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageUser

class SignUpRepository(private val sharedStorageUser:SharedPreferencesStorageUser) {
    /**FUNCIONES----------------------------------------------------------------------------------*/
    //VERIFICA SI EL USUARIO YA ESTABA REGISTRADO
   fun isUserRegistered(email: String, password: String): Boolean {
        val user = sharedStorageUser.getUser()
        return user != null && user.email.trim() == email.trim() && user.password.trim() == password.trim()
    }


    //GUARDA EL NUEVO USUARIO
   fun registerUser(email: String, password: String) {
        val user = UserEntity(0, email, password)
        sharedStorageUser.saveUser(user)
    }
}
