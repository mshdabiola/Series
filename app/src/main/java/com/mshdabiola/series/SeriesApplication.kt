package com.mshdabiola.series

import android.app.Application
import android.content.Context
import androidx.core.content.pm.PackageInfoCompat
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import com.mshdabiola.series.di.appModule
import com.mshdabiola.series.worker.SaveWorker
import com.mshdabiola.series.worker.prefsName
import com.mshdabiola.series.worker.versionKey
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber


class SeriesApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SeriesApplication)
            modules(appModule)
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
        if (version > oldV) {
            val workManager=WorkManager.getInstance(this)
            workManager
                .enqueueUniqueWork(
                    "updater",
                    ExistingWorkPolicy.REPLACE,
                    SaveWorker.getRequest()
                )
        }

    }
}
