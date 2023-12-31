package com.mshdabiola.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mshdabiola.model.data.CurrentExam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json

internal class MultiplatformSettingsImpl(
    private val settings: DataStore<Preferences>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {
    private val currentExamKey = stringPreferencesKey("currentKey")
    override suspend fun setCurrentExam(currentExam: CurrentExam) {
        withContext(Dispatchers.IO) {
            settings.edit {
                val crString = Json.encodeToString(CurrentExam.serializer(), currentExam)
                it[currentExamKey] = crString
            }

        }
    }

    override suspend fun getCurrentExam(): CurrentExam? {

        val list = settings.data.map {
            val crString = it[currentExamKey]
            if (crString == null)
                null
            else
                Json.decodeFromString(CurrentExam.serializer(), crString)
        }
//        val list = settings
//            .toBlockingSettings()
//            .decodeValueOrNull(ListSerializer(CurrentExam.serializer()), Keys.currentExamKey)
        return list.firstOrNull()
    }
}
