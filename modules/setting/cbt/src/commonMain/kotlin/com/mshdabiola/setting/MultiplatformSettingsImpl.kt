package com.mshdabiola.setting

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.UserData
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSettingsApi::class)
internal class MultiplatformSettingsImpl(
    private val settings: FlowSettings,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {
    private val userKey="userKey"
    private val currentExamKey = "currentExam"

    override val userData: Flow<UserData>
        get() = settings.getStringOrNullFlow(userKey) .map {

            if (it != null) {
                Json.decodeFromString<UserData>(it)
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
        val crString = Json.encodeToString(CurrentExam.serializer(), currentExam)

        settings.putString(currentExamKey,crString)
    }
}

override suspend fun getCurrentExam(): CurrentExam? {
    val list = settings.getStringOrNullFlow(currentExamKey).map {crString->
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
        val userDataStr = Json.encodeToString(userData)
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setThemeContrast(contrast: Contrast) {
        val userData = userData.first().copy(contrast = contrast)
        val userDataStr = Json.encodeToString(userData)
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        val userData = userData.first().copy(useDynamicColor = useDynamicColor)
        val userDataStr = Json.encodeToString(userData)
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        val userData = userData.first().copy(darkThemeConfig = darkThemeConfig)
        val userDataStr = Json.encodeToString(userData)
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        val userData = userData.first().copy(shouldHideOnboarding = shouldHideOnboarding)
        val userDataStr = Json.encodeToString(userData)
        settings.putString(userKey,userDataStr)
    }
}
