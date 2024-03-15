package com.mshdabiola.series.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.pages.Pages
import com.arkivanov.decompose.extensions.compose.pages.PagesScrollAnimation
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.mshdabiola.navigation.IPagerComponent
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.series.screen.finish.FinishScreenNav
import com.mshdabiola.series.screen.main.MainScreenNav
import com.mshdabiola.series.screen.profile.ProfileScreenNav
import com.mshdabiola.series.screen.question.QuestionScreenNav
import com.mshdabiola.series.screen.stat.StatScreenNav
import com.mshdabiola.ui.UpdateAppUi

@Composable
fun SeriesAppNavHost(appState: SeriesAppState) {
    Children(
        stack = appState.navController.stack,
        modifier = Modifier,
        animation = stackAnimation(fade() + slide()),
    ) {
        when (it.instance) {
            is IRootComponent.RootScreen.PagerScreen -> {
                PagerCom(
                    (it.instance as IRootComponent.RootScreen.PagerScreen).component,
                    onQuestion = appState.navController::navigateToQuestion

                )
            }

            is IRootComponent.RootScreen.QuestionRootScreen -> {
                QuestionScreenNav(
                    onBack = appState.navController::pop,
                    onFinish = appState.navController::navigateToFinish
                )
            }

            is IRootComponent.RootScreen.FinishRootScreen -> {
                FinishScreenNav(
                    onBack = appState.navController::pop,
                    toQuestion = appState.navController::navigateToQuestion
                )
            }

        }

    }
}



@OptIn(ExperimentalDecomposeApi::class, ExperimentalFoundationApi::class)
@Composable
fun PagerCom(
    iPagerComponent: IPagerComponent,
    onQuestion: () -> Unit = {},
) {
    val current = iPagerComponent.current.subscribeAsState().value

    Column {
        Pages(
            pages = iPagerComponent.pages,
            modifier = Modifier.weight(1f),
            onPageSelected = {},
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
        UpdateAppUi()
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

