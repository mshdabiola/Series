package com.mshdabiola.physics.worker

import androidx.work.WorkManager


fun WorkManager.getWorkLiveData() =
        this
            .getWorkInfosForUniqueWorkLiveData("updater")





