/*
 *abiola 2024
 */

package com.mshdabiola.series.editor

import android.app.Application
import com.mshdabiola.model.parentPath
import com.mshdabiola.series.di.appModules
import com.mshdabiola.series.editor.di.jankStatsModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@SkeletonApplication)
            modules(appModules, jankStatsModule)
        }
        parentPath = this.applicationContext.filesDir.path

        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }
    }
}
