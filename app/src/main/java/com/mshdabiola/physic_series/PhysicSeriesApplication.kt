package com.mshdabiola.physic_series

import android.app.Application
import com.mshdabiola.physic_series.di.appModule
import com.mshdabiola.worker.Saver
import com.mshdabiola.worker.di.workModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class PhysicSeriesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@PhysicSeriesApplication)
            modules(appModule, workModule)
        }

        Saver.initialize(applicationContext)
        Saver.saveGame(89L)
        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }
    }
}
