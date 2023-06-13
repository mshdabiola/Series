package com.mshdabiola.navigation

import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.router.pages.ChildPages
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface IPagerComponent {
    @OptIn(ExperimentalDecomposeApi::class)
    val stack: Value<ChildPages<*, PScreen>>



    fun selectedPage(int: Int)

sealed class PScreen {
    class MainRootScreen(val component1: MainComponent) : PScreen()
    class StatisticsRootScreen(val component1: StatisticComponent) : PScreen()
    class ProfileRootScreen(val component1: ProfileComponent) : PScreen()
}

}