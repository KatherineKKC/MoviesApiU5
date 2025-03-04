package com.kurokawa.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kurokawa.data.room.entities.UserEntity

@Dao
interface UserDao {

    @Insert
    suspend fun insertUser(user: UserEntity): Long

    @Query("SELECT * FROM user WHERE idFirebaseUser = :firebaseUid LIMIT 1")
    suspend fun getUserByFirebaseId(firebaseUid: String): UserEntity?

    @Query("DELETE FROM user WHERE idFirebaseUser = :firebaseUid")
    suspend fun deleteUserByFirebaseId(firebaseUid: String)

    @Query("SELECT * FROM user")
    suspend fun getAllUsers(): List<UserEntity>


}