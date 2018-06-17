package se.uav.loggbok

import android.app.Application
import org.koin.android.ext.android.startKoin
import se.uav.loggbok.di.appModule

class LoggbokApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appModule))
    }

}