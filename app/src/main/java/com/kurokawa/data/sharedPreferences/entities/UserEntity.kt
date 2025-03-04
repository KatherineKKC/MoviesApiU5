package com.kurokawa.data.sharedPreferences.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var idUser: Int,
    val email: String,
    val password: String
)
