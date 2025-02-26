package com.kurokawa.repository

import com.kurokawa.data.dataStore.entities.UserEntity
import com.kurokawa.data.dataStore.store.UserDataStore

class SignUpRepository(private val userDataStore: UserDataStore) {
    /**FUNCIONES----------------------------------------------------------------------------------*/

    suspend fun registerUser(email: String, password: String): Boolean {
        val user = UserEntity(0, email, password)
        return  userDataStore.saveUser(user)
    }
}
