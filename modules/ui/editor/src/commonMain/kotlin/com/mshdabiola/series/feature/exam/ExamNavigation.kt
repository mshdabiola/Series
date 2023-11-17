package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ExamScreenNav(
    examId :Long,
    subjectId:Long,
    onBack: () -> Unit,
) {

    val viewModel: ExamViewModel = KoinCommonViewModel(parameters = { parametersOf(examId, subjectId) })
    ExamScreen(
        onBack = onBack,
        viewModel = viewModel
    )
}

