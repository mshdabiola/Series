package com.mshdabiola.series

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.series.feature.exam.ExamScreenNav
import com.mshdabiola.series.feature.main.MainScreenNav


@Composable
fun AppNavHost(
    iRootComponent: IRootComponent,
    modifier: Modifier,
    windowSizeClass: WindowSizeClass,
) {

    Children(
        modifier = modifier,
        stack = iRootComponent.stack,
        animation = stackAnimation(slide())
    ) {

        when (val instance = it.instance) {


            is IRootComponent.RootScreen.MainRootScreen -> {
                MainScreenNav(
                    windowSizeClass,
                    onExamClick = iRootComponent::navigateToExam
                )
            }

            is IRootComponent.RootScreen.QuestionRootScreen -> {
                ExamScreenNav(
                    windowSizeClass,
                    subjectId = instance.subjectId,
                    examId = instance.examId,
                    onBack = iRootComponent::pop,
                )
            }

        }

    }


}
