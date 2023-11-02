package com.mshdabiola.setting

import com.mshdabiola.model.data.CurrentExam

interface MultiplatformSettings {

    suspend fun setCurrentExam(currentExam: CurrentExam)

    suspend fun getCurrentExam(): CurrentExam?
}
