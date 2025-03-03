package com.kurokawa.data.room.dao
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kurokawa.data.room.entities.UserEntity
import retrofit2.http.GET

@Dao
interface UserDao {


    // Insertar un nuevo usuario
    @Insert
    suspend fun insertUser(user: UserEntity): Long


}