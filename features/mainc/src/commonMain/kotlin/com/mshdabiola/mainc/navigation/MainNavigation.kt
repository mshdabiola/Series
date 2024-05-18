/*
 *abiola 2022
 */

package com.mshdabiola.mainc.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.cbtRoute
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.mainc.MainRoute
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.state.ExamType

val MAIN_ROUTE = cbtRoute[0]

fun NavController.navigateToMain(navOptions: NavOptions) = navigate(MAIN_ROUTE, navOptions)

fun NavGraphBuilder.mainScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    screenSize: ScreenSize,
    navigateToQuestion: (Int, Long, Int) -> Unit = { _, _, _ -> },
    navigateToSetting: () -> Unit = {},
) {
    composable(route = MAIN_ROUTE) {
        MainRoute(
            screenSize = screenSize,
            onShowSnackbar = onShowSnack,
            navigateToSetting = navigateToSetting,
            navigateToQuestion = {type,year,objType->
                navigateToQuestion(type.ordinal,year,objType)}
        )
    }
}
