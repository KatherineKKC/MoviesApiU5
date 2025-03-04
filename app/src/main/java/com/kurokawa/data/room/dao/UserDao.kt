package com.kurokawa.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kurokawa.data.room.entities.UserEntity

@Dao
interface UserDao {
    @Insert
    suspend fun addUser(user: UserEntity)


    @Query("SELECT * FROM user")
    suspend fun getUsers(): List<UserEntity>

    @Query("SELECT * FROM user WHERE email=:emailParameter AND password= :passwordParameter")
    suspend fun getUser(emailParameter: String, passwordParameter: String): UserEntity
}