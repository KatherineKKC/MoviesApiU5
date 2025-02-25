package com.kurokawa.data.room.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kurokawa.data.room.entities.UserEntity

@Dao
interface UserDao {

    // Obtener un usuario por su email y contraseña
    @Query("SELECT * FROM user WHERE email = :email AND password = :password LIMIT 1")
    suspend fun getUser(email: String, password: String): UserEntity?

    // Insertar un nuevo usuario
    @Insert
    suspend fun insertUser(user: UserEntity)
}