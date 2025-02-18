package com.kurokawa.repository

import com.kurokawa.data.room.dao.UserDao


class LoginRepository(private val userDao: UserDao) {

    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun validateUser(email: String, password: String): Boolean {
        val user = userDao.getUser(email,password)
       if (user != null){
           return true
       }else{
           return false
       }
    }
}