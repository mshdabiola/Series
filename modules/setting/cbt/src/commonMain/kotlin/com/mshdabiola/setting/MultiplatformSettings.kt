package com.mshdabiola.setting

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.data.UserData
import com.mshdabiola.model.data.CurrentExam
import kotlinx.coroutines.flow.Flow

interface MultiplatformSettings {

    val userData: Flow<UserData>

    suspend fun setCurrentExam(currentExam: CurrentExam)

    suspend fun getCurrentExam(): CurrentExam?

    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setThemeContrast(contrast: Contrast)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)
}
