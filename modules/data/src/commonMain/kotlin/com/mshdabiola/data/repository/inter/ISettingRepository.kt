package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.DummySetting
import kotlinx.coroutines.flow.StateFlow

interface ISettingRepository {

    val dummy: StateFlow<DummySetting>
    suspend fun setDummy(dummy: DummySetting)
}