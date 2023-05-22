package com.mshdabiola.series.nav

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.parcelable.Parcelable
import com.mshdabiola.series.feature.main.MainScreenComponent
import com.mshdabiola.series.feature.exam.ExamScreenComponent
import com.mshdabiola.series.feature.splash.SplashScreenComponent

class DefaultRootComp(
    componentContext: ComponentContext,
) : RootComp, ComponentContext by componentContext {
    private val navigation = StackNavigation<Config>()


    override val stack: Value<ChildStack<*, RootComp.Child>>
        get() = _stack
    private val _stack = childStack(
        source = navigation,
        initialConfiguration = Config.Main,
        handleBackButton = true,
        childFactory = ::factory

    )


    sealed interface Config : Parcelable {
        object Main : Config
        object Splash : Config

        data class ExamArg(val id: Long) : Config
    }

    private fun factory(config: Config, componentContext: ComponentContext): RootComp.Child {
        return when (config) {
            is Config.Splash -> RootComp.Child.SplashChild(navigateToSplash(componentContext))
            is Config.Main -> RootComp.Child.MainChild(navigateToMain(componentContext))
            is Config.ExamArg -> RootComp.Child.ExamChild(navigateToExam(config, componentContext))
        }
    }

    fun navigateToMain(componentContext: ComponentContext): MainComp {
        return MainScreenComponent(
            componentContext,
            onExamClick = { navigation.push(Config.ExamArg(it)) }

        )
    }

    fun navigateToSplash(componentContext: ComponentContext): SplashComp {
        return SplashScreenComponent(
            componentContext,
            onSplashFinished = { navigation.replaceCurrent(Config.Main) })
    }

    fun navigateToExam(arg: Config.ExamArg, componentContext: ComponentContext): ExamComp {
        return ExamScreenComponent(
            arg.id,
            componentContext,
            onBack = { navigation.pop() }
        )

    }
}

@Composable
fun RootComp(component: RootComp, modifier: Modifier) {
    Children(
        stack = component.stack,
        modifier = modifier,
        animation = stackAnimation(slide())
    ) {
        when (val child = it.instance) {

            is RootComp.Child.MainChild -> {
                child.component.render()
            }

            is RootComp.Child.SplashChild -> {
                child.component.render()
            }

            is RootComp.Child.ExamChild -> {
                child.component.render()
            }
        }
    }
}