/*
 *abiola 2022
 */

package com.mshdabiola.stat.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.stat.MainRoute
import com.mshdabiola.ui.ScreenSize

val STAT_ROUTE = cbtRoute[2]

fun NavController.navigateToStat(navOptions: NavOptions) = navigate(STAT_ROUTE, navOptions)

fun NavGraphBuilder.statScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    screenSize: ScreenSize,
) {
    composable(route = STAT_ROUTE) {
        MainRoute(
            screenSize = screenSize,
            onShowSnackbar = onShowSnack,
        )
    }
}
