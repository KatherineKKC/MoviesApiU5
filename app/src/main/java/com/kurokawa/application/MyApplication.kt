package com.kurokawa.application

import android.app.Application
import androidx.room.Room
import com.kurokawa.data.room.database.MyDataBaseRoom
import com.kurokawa.data.room.database.MovieDatabase

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    lateinit var myDataBaseRoom: MyDataBaseRoom
    lateinit var movieDatabaseRoom: MovieDatabase

    override fun onCreate() {
        super.onCreate()
        instance = this  // ✅ Instancia global

        myDataBaseRoom = Room.databaseBuilder(
            applicationContext,
            MyDataBaseRoom::class.java,
            "MyDataBaseRoom"
        ).fallbackToDestructiveMigration().build()

        movieDatabaseRoom = Room.databaseBuilder(
            applicationContext,
            MovieDatabase::class.java,
            "MovieDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}
