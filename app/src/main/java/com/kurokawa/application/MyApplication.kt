package com.kurokawa.application

import android.app.Application
import androidx.room.Room
import com.kurokawa.data.room.database.MyDataBase

class MyApplication : Application() {

    companion object {
        lateinit var instance: MyApplication
            private set
    }

    lateinit var myDataBase: MyDataBase

    override fun onCreate() {
        super.onCreate()
        instance = this  // ✅ Instancia global

        myDataBase = Room.databaseBuilder(
            applicationContext,
            MyDataBase::class.java,
            "MovieDatabase"
        ).fallbackToDestructiveMigration().build()
    }
}
