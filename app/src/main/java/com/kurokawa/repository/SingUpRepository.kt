package com.kurokawa.repository

import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.UserEntity

class SignUpRepository(private val userDao: UserDao) {
    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun isUserRegistered(email: String, password: String): Boolean {
        val user = userDao.getUser(email,password)
        return user != null
    }

    suspend fun registerUser(email: String, password: String) {
        val user = UserEntity(0, email, password)
        userDao.addUser(user)
    }
}
