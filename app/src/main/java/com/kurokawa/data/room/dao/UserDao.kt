package com.kurokawa.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kurokawa.data.room.model.User

@Dao
interface UserDao {
   @Insert
   suspend fun addUser(user: User)


   @Query("SELECT * FROM user")
   suspend fun getUsers(): List<User>

   @Query("SELECT * FROM user WHERE email=:emailParameter AND password= :passwordParameter")
   suspend fun getUser(emailParameter :String, passwordParameter:String) : User
}