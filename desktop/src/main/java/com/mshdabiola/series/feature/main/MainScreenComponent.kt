package com.mshdabiola.series.feature.main

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.mshdabiola.series.nav.MainComp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class MainScreenComponent(
    private val componentContext: ComponentContext,
    private val onExamClick: (Long,Long) -> Unit = {_,_->}
) : MainComp, ComponentContext by componentContext, KoinComponent {

    private val viewModel by inject<MainViewModel>()

    init {

        componentContext.lifecycle.doOnDestroy {
            viewModel.destroy()

        }
    }

//

    //
    @Composable
    override fun render() {
        val scope = rememberCoroutineScope()
//        LaunchedEffect(viewModel) {
//            viewModel.init(scope)
//        }

        MainScreen(viewModel, onExamClick = onExamClick)
    }
}