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
import com.arkivanov.decompose.extensions.compose.jetbrains.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.mshdabiola.finishscreen.FinishScreenNav
import com.mshdabiola.mainscreen.MainScreenNav
import com.mshdabiola.navigation.IPagerComponent
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.profilescreen.ProfileScreenNav
import com.mshdabiola.questionscreen.QuestionScreenNav
import com.mshdabiola.statisticsscreen.StatScreenNav


@Composable
fun AppNavHost(iRootComponent: IRootComponent, modifier: Modifier) {

    Column() {
        Children(
            stack = iRootComponent.stack,
            modifier = Modifier.fillMaxSize(),
            animation = stackAnimation(slide())
        ) {

            when (it.instance) {
                is IRootComponent.RootScreen.PagerScreen -> {
                    PagerCom(
                        (it.instance as IRootComponent.RootScreen.PagerScreen).component,
                        onQuestion = iRootComponent::navigateToQuestion

                    )
                }

                is IRootComponent.RootScreen.QuestionRootScreen -> {
                    QuestionScreenNav(
                        onBack = iRootComponent::pop,
                        onFinish = iRootComponent::navigateToFinish
                    )
                }

                is IRootComponent.RootScreen.FinishRootScreen -> {
                    FinishScreenNav(onBack = iRootComponent::pop)
                }

            }

        }


    }
}


@OptIn(ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun PagerCom(
    iPagerComponent: IPagerComponent,
    onQuestion: () -> Unit = {}
) {
    var current by remember {
        mutableStateOf(0)
    }
    Column {
        Pages(
            pages = iPagerComponent.stack,
            modifier = Modifier.weight(1f),
            onPageSelected = { current = it },
            scrollAnimation = PagesScrollAnimation.Default
        ) { _, page ->
            when (page) {
                is IPagerComponent.PScreen.MainRootScreen -> {
                    MainScreenNav(onQuestion = onQuestion)
                }

                is IPagerComponent.PScreen.ProfileRootScreen -> {
                    ProfileScreenNav()
                }

                is IPagerComponent.PScreen.StatisticsRootScreen -> {
                    StatScreenNav()
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