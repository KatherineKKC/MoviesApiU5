package com.kurokawa.data.repository

import com.kurokawa.data.room.dao.UserDao


class LoginRepository(private val userDao: UserDao) {

    //Obtiene el usuario con la misma contraseña e email de la db room
    suspend fun validateUser(email: String, password: String): Boolean {
        val user = userDao.getUser(email, password)
        if (user != null) {
            return true
        } else {
            return false
        }
    }
}