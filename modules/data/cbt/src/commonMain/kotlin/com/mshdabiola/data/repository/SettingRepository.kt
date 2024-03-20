package com.mshdabiola.data.repository

import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.setting.MultiplatformSettings

internal class SettingRepository(private val settings: MultiplatformSettings) : ISettingRepository {
    override suspend fun setCurrentExam(currentExam: CurrentExam) {
        settings.setCurrentExam(currentExam)
    }

    override suspend fun getCurrentExam(): CurrentExam? {
        return settings.getCurrentExam()
    }
}
