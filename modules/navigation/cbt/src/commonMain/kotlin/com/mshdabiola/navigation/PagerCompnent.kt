package com.mshdabiola.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.pages.Pages
import com.arkivanov.decompose.router.pages.PagesNavigation
import com.arkivanov.decompose.router.pages.childPages
import com.arkivanov.decompose.router.pages.select
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.value.operator.map
import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize

class PagerComponent(componentContext: ComponentContext) : IPagerComponent,
    ComponentContext by componentContext {

    @OptIn(ExperimentalDecomposeApi::class)
    private val navigation = PagesNavigation<Config2>()

    @OptIn(ExperimentalDecomposeApi::class)
    override val pages: Value<ChildPages<*, IPagerComponent.PScreen>> = childPages(
        source = navigation,
        initialPages = {
            Pages(
                items = listOf(
                    Config2.Main, Config2.Statistic//, Config2.Profile
                ),
                selectedIndex = 0
            )
        }
    ) { configuration: Config2, componentContext: ComponentContext ->
        when (configuration) {
            is Config2.Main -> IPagerComponent.PScreen.MainRootScreen(MainComponent(componentContext))
            is Config2.Statistic -> IPagerComponent.PScreen.StatisticsRootScreen(
                StatisticComponent(
                    componentContext
                )
            )

//            is Config2.Profile -> IPagerComponent.PScreen.ProfileRootScreen(
//                ProfileComponent(
//                    componentContext
//                )
//            )
        }
    }

    @OptIn(ExperimentalDecomposeApi::class)
    override val current = pages.map { it.selectedIndex }

    @OptIn(ExperimentalDecomposeApi::class)
    override fun selectedPage(int: Int) {
        navigation.select(int)
    }

    private sealed interface Config2 : Parcelable {

        @Parcelize
        data object Main : Config2 {
            private fun readResolve(): Any = Main
        }

        @Parcelize
        data object Statistic : Config2 {
            private fun readResolve(): Any = Statistic
        }

//        @Parcelize
//        data object Profile : Config2 {
//            private fun readResolve(): Any = Profile
//        }


    }

}
