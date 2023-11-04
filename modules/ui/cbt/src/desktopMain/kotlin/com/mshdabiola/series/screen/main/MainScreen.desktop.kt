package com.mshdabiola.series.screen.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.toImmutableList
@Preview
@Composable
actual fun MainScreenPreview() {
    MainScreen(
        mainState = MainState(
            listOfAllExams = listOf(
                ExamUiState(
                    id = 7353L,
                    subjectID = 7692L,
                    year = 6756L,
                    subject = "Taya",
                    isObjOnly = false,
                    examTime = 400
                ),
            ).toImmutableList(),
            currentExam = ExamUiState(
                id = 0L,
                subjectID = 0L,
                year = 1556L,
                subject = "Math",
                isObjOnly = false,
                examTime = 0L
            )
        )
    )
}