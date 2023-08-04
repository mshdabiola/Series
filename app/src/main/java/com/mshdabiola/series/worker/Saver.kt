package com.mshdabiola.series.worker

import androidx.work.WorkManager


fun WorkManager.getWorkLiveData() =
        this
            .getWorkInfosForUniqueWorkLiveData("updater")





