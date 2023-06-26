package com.mshdabiola.worker

import android.content.Context
import androidx.startup.AppInitializer
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.mshdabiola.worker.work.SaveWorker
import kotlinx.coroutines.flow.map
import timber.log.Timber
import java.util.UUID

object Saver {
    lateinit var workManager: WorkManager
    fun initialize(context: Context) {
        val saver = AppInitializer.getInstance(context)
            .initializeComponent(SaverInitializer::class.java)
        workManager = saver.workManager
    }

    private fun save(workName: String, id: Long) {
        workManager
            .enqueueUniqueWork(
                workName,
                ExistingWorkPolicy.REPLACE,
                SaveWorker.startUpSaveWork(id)
            )

    }

    fun saveGame(id: Long) {

        save("updater", id = id)
    }

    fun getWorkLiveData()=
        workManager
            .getWorkInfosForUniqueWorkLiveData("updater")



}


