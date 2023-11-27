package com.mshdabiola.series.editor

import android.app.Application
import com.mshdabiola.model.parentPath
import com.mshdabiola.series.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class SeriesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SeriesApplication)
            modules(appModules)
        }
        parentPath = this.applicationContext.filesDir.path

        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }


    }
}
