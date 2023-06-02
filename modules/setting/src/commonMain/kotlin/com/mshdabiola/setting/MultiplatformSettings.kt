package com.mshdabiola.setting

import com.mshdabiola.model.DummySetting
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MultiplatformSettings {

    val name: Flow<String>
    val dummy: StateFlow<DummySetting>

    suspend fun setName(name: String)

    suspend fun setDummy(dummy: DummySetting)

}
