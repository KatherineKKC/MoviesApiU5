package com.kurokawa.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.UserEntity

@Database(entities = [ UserEntity::class], version = 2)
abstract  class MyDataBaseRoom : RoomDatabase(){
    abstract fun userDao(): UserDao
}
