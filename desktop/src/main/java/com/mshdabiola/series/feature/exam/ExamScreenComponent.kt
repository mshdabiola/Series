package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.mshdabiola.series.nav.ExamComp
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class ExamScreenComponent(
    private val id: Long,
    private val componentContext: ComponentContext,
    private val onBack: () -> Unit = {}
) : ExamComp, ComponentContext by componentContext, KoinComponent {

    private val viewModel by inject<ExamViewModel>(parameters = { parametersOf(id) })

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
        ExamScreen(viewModel, onBack = onBack)


    }
}