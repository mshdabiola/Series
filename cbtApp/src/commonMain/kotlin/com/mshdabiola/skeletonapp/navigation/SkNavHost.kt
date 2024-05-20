/*
 *abiola 2022
 */

package com.mshdabiola.skeletonapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import com.mshdabiola.finish.navigation.finishScreen
import com.mshdabiola.finish.navigation.navigateToFinish
import com.mshdabiola.mainc.navigation.MAIN_ROUTE
import com.mshdabiola.mainc.navigation.mainScreen
import com.mshdabiola.profile.navigation.profileScreen
import com.mshdabiola.question.navigation.navigateToQuestion
import com.mshdabiola.question.navigation.questionScreen
import com.mshdabiola.setting.navigation.navigateToSetting
import com.mshdabiola.setting.navigation.settingScreen
import com.mshdabiola.skeletonapp.ui.SkAppState
import com.mshdabiola.stat.navigation.statScreen

@Composable
fun SkNavHost(
    appState: SkAppState,
    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
    modifier: Modifier = Modifier,
    startDestination: String = MAIN_ROUTE,
) {
    val navController = appState.navController
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        mainScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            navigateToSetting = navController::navigateToSetting,
            navigateToQuestion = navController::navigateToQuestion,
        )
        questionScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
            navigateToFinish = navController::navigateToFinish
        )
        finishScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
            navigateToQuestion = navController::navigateToQuestion
        )

        statScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
        )

        profileScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
        )


        settingScreen(
            screenSize = appState.screenSize,
            onShowSnack = onShowSnackbar,
            onBack = navController::popBackStack,
        )
    }
}
