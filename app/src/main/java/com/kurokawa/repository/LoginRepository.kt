package com.kurokawa.repository

import com.kurokawa.data.dataStore.entities.UserEntity
import com.kurokawa.data.dataStore.store.UserDataStore


class LoginRepository(private val userDataStore: UserDataStore) {

    /**FUNCIONES----------------------------------------------------------------------------------*/
    fun validateUser(email: String, password: String): Boolean {
        UserEntity(0, email, password)
        val user = userDataStore.getUser(email, password)

        if (user != null) {
            return true
        }
        return false


    }
}