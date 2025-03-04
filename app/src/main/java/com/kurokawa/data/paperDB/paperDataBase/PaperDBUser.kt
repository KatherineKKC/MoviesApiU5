package com.kurokawa.data.paperDB.paperDataBase

import com.kurokawa.data.paperDB.entities.UserEntity
import io.paperdb.Paper

class PaperDBUser {

    private val USER_KEY = "user_data"

    //GUARDA EL USUARIO EN PAPER
    fun saveUser(user: UserEntity) {
        Paper.book().write(USER_KEY, user)
    }

    //OBTIENE EL USUARIO DE PAPER
    fun getUser(): UserEntity? {
        return Paper.book().read(USER_KEY, null)
    }


}
