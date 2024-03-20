package com.mshdabiola.series.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.series.screen.exam.ExamScreenNav
import com.mshdabiola.series.screen.main.MainScreenNav
import com.mshdabiola.series.screen.setting.SettingScreenNav

@Composable
fun SkeletonAppNavHost(appState: SkAppState) {
    Children(
        stack = appState.navController.stack,
        modifier = Modifier,
        animation = stackAnimation(fade() + slide()),
    ) {
        when (val instance = it.instance) {
            is IRootComponent.RootScreen.MainRootScreen -> {
                MainScreenNav(
                    appState.windowSizeClass,
                    onExamClick = appState.navController::navigateToExam,
                    onSetting = appState.navController::navigateToSetting,
                )
            }

            is IRootComponent.RootScreen.QuestionRootScreen -> {
                ExamScreenNav(
                    appState.windowSizeClass,
                    subjectId = instance.subjectId,
                    examId = instance.examId,
                    onBack = appState.navController::pop,
                )
            }

            is IRootComponent.RootScreen.SettingRootScreen -> {
                SettingScreenNav(
                    onBack = appState.navController::pop,
                )
            }
        }
    }
}
