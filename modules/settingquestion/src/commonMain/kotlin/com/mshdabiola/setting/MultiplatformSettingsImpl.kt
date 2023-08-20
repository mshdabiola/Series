package com.mshdabiola.setting

import com.mshdabiola.model.data.CurrentExam
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toBlockingSettings
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer

@OptIn(ExperimentalSettingsApi::class)
internal class MultiplatformSettingsImpl(
    private val settings: FlowSettings,
    private val coroutineDispatcher: CoroutineDispatcher
) : MultiplatformSettings {
    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun setCurrentExam(currentExam: CurrentExam?) {
        withContext(Dispatchers.IO) {
            settings
                .toBlockingSettings()
                .encodeValue(
                    ListSerializer(CurrentExam.serializer()), Keys.currentExamKey,
                    if (currentExam == null) emptyList() else listOf(currentExam)
                )
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getCurrentExam(): CurrentExam? {

        val list = settings
            .toBlockingSettings()
            .decodeValueOrNull(ListSerializer(CurrentExam.serializer()), Keys.currentExamKey)
        return list?.firstOrNull()
    }
}
