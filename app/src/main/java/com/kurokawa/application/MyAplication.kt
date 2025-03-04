package com.kurokawa

import android.app.Application
import com.kurokawa.utils.AppModule
import io.paperdb.Paper
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyAplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Paper.init(this)

        startKoin {
            androidContext(this@MyAplication)
            modules(AppModule.module)
        }
    }
}
