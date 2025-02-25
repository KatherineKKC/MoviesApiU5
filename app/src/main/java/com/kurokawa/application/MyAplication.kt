package com.kurokawa

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.kurokawa.utils.AppModule

class MyAplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@MyAplication)
            modules(AppModule.module)
        }
    }
}
