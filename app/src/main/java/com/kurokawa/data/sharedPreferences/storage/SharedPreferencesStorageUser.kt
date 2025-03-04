package com.kurokawa.data.sharedPreferences.storage

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.kurokawa.data.sharedPreferences.entities.UserEntity


class SharedPreferencesStorageUser(private val context: Context) {
    /**VARIABLES ---------------------------------------------------------------------------------*/
    private val sharedStorageUser = "UserSharedPreferencesDB"
    private val sharedPreferencesStorage: SharedPreferences =
        context.getSharedPreferences(sharedStorageUser, Context.MODE_PRIVATE)
    private val gson = Gson()


    /**FUNCIONES ---------------------------------------------------------------------------------*/
    // Guardar usuario en SharedPreferences
    fun saveUser(user: UserEntity) {
        val editor = sharedPreferencesStorage.edit()
        val json = gson.toJson(user)
        editor.putString("user_data", json)
        editor.apply()
    }


    // Obtener usuario de SharedPreferences
    fun getUser(): UserEntity? {
        val json = sharedPreferencesStorage.getString("user_data", null)
        return if (json != null) {
            gson.fromJson(json, UserEntity::class.java)
        } else {
            null
        }
    }

    // Actualizar datos del usuario
    fun updateUser(updatedUser: UserEntity) {
        saveUser(updatedUser)
    }

    // Eliminar usuario
    fun deleteUser() {
        val editor = sharedPreferencesStorage.edit()
        editor.remove("user_data")
        editor.apply()
    }
}
