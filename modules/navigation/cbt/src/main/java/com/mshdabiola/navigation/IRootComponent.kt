package com.mshdabiola.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface IRootComponent {
    val stack: Value<ChildStack<*, RootScreen>>


    fun navigateToPager()

    fun navigateToFinish()
    fun navigateToQuestion()


    fun pop()
    sealed class RootScreen {

        class PagerScreen(val component: IPagerComponent) : RootScreen()
        class QuestionRootScreen(val component: QuestionComponent) : RootScreen()
        class FinishRootScreen(val component: FinishComponent) : RootScreen()

    }
}