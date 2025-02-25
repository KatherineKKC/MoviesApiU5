package com.kurokawa.repository

import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageMovies
import com.kurokawa.data.sharedPreferences.storage.SharedPreferencesStorageUser


class LoginRepository(private val sharedStorageUser: SharedPreferencesStorageUser) {

    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun validateUser(email: String, password: String): Boolean {
        val user = sharedStorageUser.getUser()
        return user!= null && user.email.trim() == email.trim() && user.password.trim() == password.trim()
    }
}