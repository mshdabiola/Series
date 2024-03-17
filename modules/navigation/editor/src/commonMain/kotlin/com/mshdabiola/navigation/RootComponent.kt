package com.mshdabiola.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable

class RootComponent(
    componentContext: ComponentContext,
) : IRootComponent, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, IRootComponent.RootScreen>>
        get() = _stack

    override fun navigateToFinish() {
        navigation.push(Config.Main)
    }

    override fun navigateToExam(examId: Long, subjectId: Long) {
        navigation.push(Config.Exam(examId, subjectId))
    }

    override fun navigateToSetting() {
        navigation.push(Config.Setting)
    }

    override fun pop() {
        navigation.pop()
    }

    private val _stack = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::factory,
    )

    @Serializable
    private sealed interface Config {

        @Serializable
        data class Exam(val examId: Long, val subjectId: Long) : Config

        @Serializable
        data object Main : Config

        @Serializable
        data object Setting : Config

    }

    private fun factory(
        config: Config,
        componentContext: ComponentContext,
    ): IRootComponent.RootScreen {
        return when (config) {
            is Config.Main -> IRootComponent.RootScreen.MainRootScreen(
                navigateToMain(
                    componentContext,
                ),
            )

            is Config.Exam -> IRootComponent.RootScreen.QuestionRootScreen(
                examId = config.examId,
                subjectId = config.subjectId,
                navigateToExam(
                    componentContext,
                ),
            )
            is Config.Setting->IRootComponent.RootScreen.SettingRootScreen(
                navigateToSetting(componentContext)
            )
        }
    }

    private fun navigateToExam(componentContext: ComponentContext): QuestionComponent {
        return QuestionComponent(componentContext)
    }

    private fun navigateToMain(componentContext: ComponentContext): MainComponent {
        return MainComponent(componentContext)
    }

    private fun navigateToSetting(componentContext: ComponentContext): SettingComponent {
        return SettingComponent(componentContext)
    }
}
