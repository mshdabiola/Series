package com.mshdabiola.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class RootComponent(
    componentContext: ComponentContext,
) : IRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, IRootComponent.RootScreen>>
        get() = _stack


    override fun navigateToPager() {
        navigation.bringToFront(Config.Pager)
    }

    override fun navigateToFinish() {
        navigation.push(Config.Finish)
    }

    override fun navigateToQuestion() {
        navigation.push(Config.Questions)
    }

    override fun pop() {
        navigation.pop()
    }


    private val _stack = childStack(
        source = navigation,
        initialConfiguration = Config.Pager,
        handleBackButton = true,
        childFactory = ::factory
    )


    private sealed interface Config : Parcelable {

        @Parcelize
        object Pager : Config


        @Parcelize
        object Questions : Config

        @Parcelize
        object Finish : Config

    }

    private fun factory(
        config: Config,
        componentContext: ComponentContext
    ): IRootComponent.RootScreen {
        return when (config) {
            is Config.Pager -> IRootComponent.RootScreen.PagerScreen(PagerComponent(componentContext))

            is Config.Finish -> IRootComponent.RootScreen.FinishRootScreen(
                navigateToFinish(
                    componentContext
                )
            )

            is Config.Questions -> IRootComponent.RootScreen.QuestionRootScreen(
                navigateToQuestion(
                    componentContext
                )
            )


        }
    }


    private fun navigateToQuestion(componentContext: ComponentContext): QuestionComponent {
        return QuestionComponent(componentContext)
    }

    private fun navigateToFinish(componentContext: ComponentContext): FinishComponent {
        return FinishComponent(componentContext)
    }


}
