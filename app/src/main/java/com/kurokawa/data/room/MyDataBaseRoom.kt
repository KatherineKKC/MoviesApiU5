package com.kurokawa.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.model.User

@Database(entities = [ User::class], version = 1)
abstract  class MyDataBaseRoom : RoomDatabase(){
    abstract fun userDao(): UserDao
}