package com.mshdabiola.series.feature.exam

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ExamScreenNav(
    windowSizeClass: WindowSizeClass,
    examId: Long,
    subjectId: Long,
    onBack: () -> Unit,
) {

    val viewModel: ExamViewModel = KoinCommonViewModel(
        key = "id$examId subId$subjectId",
        parameters = { parametersOf(examId, subjectId) })
    ExamScreen(
        windowSizeClass = windowSizeClass,
        onBack = onBack,
        viewModel = viewModel
    )
}

