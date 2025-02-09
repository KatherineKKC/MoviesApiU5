package com.kurokawa.application

import android.app.Application
import androidx.room.Room
import com.kurokawa.data.room.MyDataBaseRoom

class MyApplication : Application(){
    lateinit var room : MyDataBaseRoom
    override fun onCreate() {
        super.onCreate()
        room = Room.databaseBuilder(
            applicationContext,
            MyDataBaseRoom::class.java,
            "MyDataBaseRoom"
        ).fallbackToDestructiveMigration().build()
    }
}