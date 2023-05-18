package com.mshdabiola.data.repository

import com.mshdabiola.model.DummySetting
import com.mshdabiola.setting.MultiplatformSettings
import kotlinx.coroutines.flow.StateFlow

internal class SettingRepository(private val settings: MultiplatformSettings) : ISettingRepository {
    override val dummy: StateFlow<DummySetting>
        get() = settings.dummy


    override suspend fun setDummy(dummy: DummySetting) {
        settings.setDummy(dummy)
    }

}