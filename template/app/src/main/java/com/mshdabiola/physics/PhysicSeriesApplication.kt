package com.mshdabiola.physics

import android.app.Application
import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import com.mshdabiola.physics.di.appModule
import com.mshdabiola.worker.Saver
import com.mshdabiola.worker.di.workModule
import com.mshdabiola.worker.util.prefsName
import com.mshdabiola.worker.util.versionKey
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

        val share = this.applicationContext.getSharedPreferences(prefsName, Context.MODE_PRIVATE)

        val oldV = share.getLong(versionKey, 0L)
        val info =
            applicationContext.packageManager.getPackageInfo(applicationContext.packageName, 0)
        val version = PackageInfoCompat.getLongVersionCode(info)
        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }

        Timber.e("saved v $oldV version $version")
        Saver.initialize(applicationContext)
         if (version > oldV) {
        Saver.saveGame(89L)
          }

    }
}
