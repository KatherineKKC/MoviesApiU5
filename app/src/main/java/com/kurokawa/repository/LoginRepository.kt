package com.kurokawa.repository

import com.kurokawa.application.MyApplication
import com.kurokawa.data.room.dao.UserDao


class LoginRepository(private val userDao :UserDao) {
    private lateinit var applicacion : MyApplication

    suspend fun validateUser(email: String, password: String): Boolean {
        val user = applicacion.myDataBaseRoom.userDao().getUser(email,password)
        if(user != null){
            return true
        }else{
            return false
        }
    }
}