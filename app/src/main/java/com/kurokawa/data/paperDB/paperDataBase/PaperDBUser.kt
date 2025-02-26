package com.kurokawa.data.paperDB.paperDataBase

import com.kurokawa.data.paperDB.entities.UserEntity
import io.paperdb.Paper

class PaperDBUser {

    private val USER_KEY = "user_data"

    /** 🔹 Guardar usuario en PaperDB */
    fun saveUser(user: UserEntity) {
        Paper.book().write(USER_KEY, user)
    }

    /** 🔹 Obtener usuario desde PaperDB */
    fun getUser(): UserEntity? {
        return Paper.book().read(USER_KEY, null)
    }


}
