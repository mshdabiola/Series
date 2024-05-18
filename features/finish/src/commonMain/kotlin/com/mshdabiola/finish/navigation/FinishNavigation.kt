/*
 *abiola 2022
 */

package com.mshdabiola.finish.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.finish.FinishRoute
import com.mshdabiola.finish.FinishViewModel
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.ScreenSize

const val FINISH_ROUTE ="finish_route"

fun NavController.navigateToFinish() = navigate(FINISH_ROUTE)

fun NavGraphBuilder.finishScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToQuestion: (Int, Long, Int) -> Unit ,
    screenSize: ScreenSize,
) {
    composable(route = FINISH_ROUTE) {
        val viewModel: FinishViewModel = KoinCommonViewModel()

        FinishRoute(
            screenSize = screenSize,
            onBack = onBack,
            onShowSnackbar = onShowSnack,
            navigateToQuestion = navigateToQuestion,
            viewModel = viewModel
        )
    }
}
