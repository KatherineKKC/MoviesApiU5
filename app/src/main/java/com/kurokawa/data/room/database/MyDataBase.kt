package com.kurokawa.data.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.dao.UserDao
import com.kurokawa.data.room.entities.MovieEntity
import com.kurokawa.data.room.entities.UserEntity




@Database(entities = [ UserEntity::class, MovieEntity::class], version = 7)
abstract  class MyDataBase : RoomDatabase(){
    abstract fun userDao(): UserDao
    abstract fun movieDao(): MovieDao
}