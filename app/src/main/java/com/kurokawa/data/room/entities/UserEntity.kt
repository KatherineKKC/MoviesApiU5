package com.kurokawa.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var idUser: Int =0,
    val idFirebaseUser: String,
    val email:String,
    val displayName: String?,
    val imagePath: String?
)
