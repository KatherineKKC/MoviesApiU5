package com.kurokawa.application

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import appModule
import com.google.firebase.FirebaseApp
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        /**INICIALIZACION DE FIREBASE Y KOIN INYECTION */
        FirebaseApp.initializeApp(this)
        createNotificationChannel()
        startKoin {
            androidContext(this@MyApplication)
            modules(appModule)
        }

    }

    /**INICIALIZACION DE FIREBASE Y KOIN INYECTION */
    //NOTIFICACIONES EN LA APP
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "default_channel", // ID del canal (debe coincidir con el del manifest)
                "Notificaciones Generales", // Nombre visible en la configuración
                NotificationManager.IMPORTANCE_HIGH // Prioridad alta
            )
            channel.description = "Canal para notificaciones generales de la app"

            val manager = getSystemService(NotificationManager::class.java)
            manager?.createNotificationChannel(channel)
        }
    }


}
