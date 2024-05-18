/*
 *abiola 2022
 */

package com.mshdabiola.profile.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.profile.ProfileRoute
import com.mshdabiola.ui.ScreenSize

val PROFILE_ROUTE = cbtRoute[1]

fun NavController.navigateToProfile(navOptions: NavOptions) = navigate(PROFILE_ROUTE, navOptions)

fun NavGraphBuilder.profileScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    screenSize: ScreenSize,
) {
    composable(route = PROFILE_ROUTE) {
        ProfileRoute(
            screenSize = screenSize,
            onShowSnackbar = onShowSnack,
        )
    }
}
