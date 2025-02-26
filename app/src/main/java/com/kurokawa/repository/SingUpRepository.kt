package com.kurokawa.repository

import com.kurokawa.data.paperDB.entities.UserEntity
import com.kurokawa.data.paperDB.paperDataBase.PaperDBUser

class SignUpRepository(private val paperDBUser:PaperDBUser) {
    /**FUNCIONES----------------------------------------------------------------------------------*/
   fun isUserRegistered(email: String, password: String): Boolean {
        val user = paperDBUser.getUser()
        return user != null && user.email.trim() == email.trim() && user.password.trim() == password.trim()
    }


   fun registerUser(email: String, password: String) {
        val user = UserEntity(0, email, password)
        paperDBUser.saveUser(user)
    }
}
