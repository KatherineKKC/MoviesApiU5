package com.kurokawa.data.dataStore.store


import android.content.Context
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.google.gson.Gson
import com.kurokawa.data.dataStore.entities.MovieEntity
import com.kurokawa.data.dataStore.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UserDataStore(private val context: Context) {

    companion object {
        private val USER_KEY = stringPreferencesKey("user")
    }

    private val gson = Gson()

    /** 🔹 Guardar usuario */
    suspend fun saveUser(user: UserEntity): Boolean {
            val existingUser = getUser(user.email, user.password)
            if (existingUser != null) {
                return false
            }

            val jsonUser = gson.toJson(user)
            context.dataStore.edit { preferences ->
                preferences[USER_KEY] = jsonUser
            }
            return true // ✅ Usuario guardado correctamente
        }



    fun getUser(email: String, password: String): UserEntity? {
        val preferences = runBlocking { context.dataStore.data.firstOrNull() ?: emptyPreferences() }
        val jsonUser = preferences[USER_KEY] ?: return null
        val user = gson.fromJson(jsonUser, UserEntity::class.java)
        if (user.email.trim() == email.trim() && user.email != null  && user.password.trim() == password.trim() && user.password != null) user
        return user
    }



}
