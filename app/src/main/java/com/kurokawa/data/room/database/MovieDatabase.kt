// MovieDatabase.kt
package com.kurokawa.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kurokawa.data.room.dao.MovieDao
import com.kurokawa.data.room.entities.Movies

@Database(entities = [Movies::class], version = 3, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): MovieDao
} 