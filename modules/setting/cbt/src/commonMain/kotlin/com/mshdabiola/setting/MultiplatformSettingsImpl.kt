package com.mshdabiola.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import com.mshdabiola.model.data.CurrentExam
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

internal class MultiplatformSettingsImpl(
    private val settings: DataStore<Preferences>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {
    private val currentExamKey = stringPreferencesKey("currentKey")
    private val userDataKey = stringPreferencesKey("UserData")

    override val userData: Flow<UserData>
        get() = settings.data.map {
            val userData = it[userDataKey]
            if (userData != null) {
                Json.decodeFromString<UserDataSer>(userData).toData()
            } else {
                UserData(
                    themeBrand = ThemeBrand.DEFAULT,
                    darkThemeConfig = DarkThemeConfig.LIGHT,
                    useDynamicColor = false,
                    shouldHideOnboarding = false,
                    contrast = Contrast.Normal,
                )
            }
        }

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
            if (crString == null) {
                null
            } else {
                Json.decodeFromString(CurrentExam.serializer(), crString)
            }
        }
//        val list = settings
//            .toBlockingSettings()
//            .decodeValueOrNull(ListSerializer(CurrentExam.serializer()), Keys.currentExamKey)
        return list.firstOrNull()
    }

    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        val userData = userData.first().copy(themeBrand = themeBrand)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setThemeContrast(contrast: Contrast) {
        val userData = userData.first().copy(contrast = contrast)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        val userData = userData.first().copy(useDynamicColor = useDynamicColor)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        val userData = userData.first().copy(darkThemeConfig = darkThemeConfig)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        val userData = userData.first().copy(shouldHideOnboarding = shouldHideOnboarding)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }
}
