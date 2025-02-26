package com.kurokawa.repository

import com.kurokawa.data.paperDB.paperDataBase.PaperDBUser


class LoginRepository(private val paperDBUser: PaperDBUser) {

    /**FUNCIONES----------------------------------------------------------------------------------*/
    suspend fun validateUser(email: String, password: String): Boolean {
        val user = paperDBUser.getUser()
        return user!= null && user.email.trim() == email.trim() && user.password.trim() == password.trim()
    }
}