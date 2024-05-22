/*
 *abiola 2024
 */

package com.mshdabiola.skeletonapp

import android.app.Application
import timber.log.Timber

class SkeletonApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        if (packageName.contains("debug")) {
            Timber.plant(Timber.DebugTree())
            Timber.e("log on app create")
        }
    }
}
