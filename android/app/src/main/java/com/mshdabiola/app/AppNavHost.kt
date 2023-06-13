package com.mshdabiola.app

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.pages.Pages
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.mshdabiola.detail.DetailScreenn
import com.mshdabiola.mainscreen.MainScreenNav
import com.mshdabiola.navigation.IPagerComponent
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.navigation.PagerComponent
import com.mshdabiola.navigation.StatisticComponent


@Composable
fun AppNavHost(iRootComponent: IRootComponent, modifier: Modifier) {

    Column() {
        Children(
            stack = iRootComponent.stack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation { child, otherChild, direction -> slide() }
        ) {

            when (it.instance) {
                is IRootComponent.RootScreen.PagerScreen -> {
                    PagerCom((it.instance as IRootComponent.RootScreen.PagerScreen).component)
                }

                is IRootComponent.RootScreen.QuestionRootScreen -> {
                    DetailScreenn {

                    }
                }

                is IRootComponent.RootScreen.FinishRootScreen -> {
                    DetailScreenn {

                    }
                }

            }

        }




}
}


@OptIn(ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun PagerCom(iPagerComponent: IPagerComponent) {
    var current by remember {
        mutableStateOf(0)
    }
    Column {
        Pages(
            pages = iPagerComponent.stack,
            modifier = Modifier.weight(1f),
            onPageSelected = {current=it}
        ) { _, page ->
            when(page){
                is IPagerComponent.PScreen.MainRootScreen -> {
                    MainScreenNav { //IRootComponent.navigateToDetail()
                    }
                }

                is IPagerComponent.PScreen.ProfileRootScreen -> {
                    MainScreenNav { //IRootComponent.navigateToDetail()
                    }
                }

                is IPagerComponent.PScreen.StatisticsRootScreen-> {
                    DetailScreenn {

                    }
                }
            }


        }
        NavigationBar {
            NavigationBarItem(selected = current == 0,
                onClick = {
                    iPagerComponent.selectedPage(0)
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "home") })

            NavigationBarItem(selected = current == 1,
                onClick = {
                    iPagerComponent.selectedPage(1)
                },
                icon = { Icon(imageVector = Icons.Default.Star, contentDescription = "home") })
            NavigationBarItem(selected = current == 2,
                onClick = {
                    iPagerComponent.selectedPage(2)
                },
                icon = { Icon(imageVector = Icons.Default.Home, contentDescription = "home") })
        }
    }

}