/*
 *abiola 2024
 */

package com.mshdabiola.setting

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import kotlinx.serialization.Serializable

/**
 * Class summarizing user interest data
 */
@Serializable
data class UserDataSer(
    val themeBrand: ThemeBrand,
    val darkThemeConfig: DarkThemeConfig,
    val useDynamicColor: Boolean,
    val shouldHideOnboarding: Boolean,
    val contrast: Contrast,
)

fun UserData.toSer() =
    UserDataSer(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)

fun UserDataSer.toData() =
    UserData(themeBrand, darkThemeConfig, useDynamicColor, shouldHideOnboarding, contrast)
