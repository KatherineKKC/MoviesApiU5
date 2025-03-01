package com.kurokawa.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.auth.FirebaseUser

@Entity(tableName = "user")
data class UserEntity(
    @PrimaryKey(autoGenerate = true) var idUser: Int,
    val idFirebaseUser: String,
    val email :String,
    val displayName: String,
    val imagePath:String
)
