package com.mshdabiola.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface IRootComponent {
    val stack: Value<ChildStack<*, RootScreen>>

    fun navigateToFinish()
    fun navigateToExam(examId: Long, subjectId: Long)

    fun navigateToSetting()


    fun pop()
    sealed class RootScreen {

        class QuestionRootScreen(
            val examId: Long,
            val subjectId: Long,
            val component: QuestionComponent,
        ) : RootScreen()

        class MainRootScreen(val component: MainComponent) : RootScreen()
        class SettingRootScreen(val component: SettingComponent) : RootScreen()
    }
}
