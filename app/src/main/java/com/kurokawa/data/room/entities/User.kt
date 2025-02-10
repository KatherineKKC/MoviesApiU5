package com.kurokawa.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey(autoGenerate = true) var idUser: Int,
     val email :String,
     val password :String
)
