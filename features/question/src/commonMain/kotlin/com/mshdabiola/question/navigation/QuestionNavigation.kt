/*
 *abiola 2022
 */

package com.mshdabiola.question.navigation

import androidx.annotation.VisibleForTesting
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.mshdabiola.designsystem.icon.mainRoute
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.question.QuestionRoute
import com.mshdabiola.question.QuestionViewModel
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.state.ExamType
import org.koin.core.parameter.parametersOf

const val QUESTION_ROUTE = "question_route"


@VisibleForTesting
internal const val QUESTION_ID_EXAM_TYPE = "examtype"
internal const val QUESTION_ID_YEAR = "year"
internal const val QUESTION_ID_INDEX = "index"




fun NavController.navigateToQuestion(examType:Int,year:Long,typeIndex:Int) =
    navigate(
        route = "$QUESTION_ROUTE/$examType/$year/$typeIndex",
    )

fun NavGraphBuilder.questionScreen(
    onShowSnack: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToFinish: () -> Unit,
    screenSize: ScreenSize,

) {
    composable(
        route = "$QUESTION_ROUTE/{$QUESTION_ID_EXAM_TYPE}/{$QUESTION_ID_YEAR}/{$QUESTION_ID_INDEX}",
        arguments = listOf(
            navArgument(QUESTION_ID_EXAM_TYPE) {
                type = NavType.IntType
            },
            navArgument(QUESTION_ID_YEAR) {
                type = NavType.LongType
            },
            navArgument(QUESTION_ID_INDEX){
                type= NavType.IntType
            }
        ),
        enterTransition = {
            slideIntoContainer(towards = AnimatedContentTransitionScope.SlideDirection.Left)
        },
        exitTransition = {
            slideOutOfContainer(towards = AnimatedContentTransitionScope.SlideDirection.Right)
        },
    ) { backStack->

        val year = backStack.arguments!!.getLong(QUESTION_ID_YEAR)
        val exam = backStack.arguments!!.getInt(QUESTION_ID_EXAM_TYPE)
        val index = backStack.arguments!!.getInt(QUESTION_ID_INDEX)


        val viewModel: QuestionViewModel = KoinCommonViewModel(parameters = {
            parametersOf(
                exam,year,index
            )
        })

        QuestionRoute(
            screenSize = screenSize,
            onBack = onBack,
            onShowSnackbar = onShowSnack,
            navigateToFinish = navigateToFinish,
            viewModel = viewModel
        )
    }
}
