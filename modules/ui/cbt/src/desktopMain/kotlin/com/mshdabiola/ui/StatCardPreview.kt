package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList

@Preview
@Composable
internal actual fun StatCardPreview() {
//    StartCard(
//        exams = listOf(ExamUiState(
//            id = 1,
//            subjectID = 2,
//            year = 2015,
//            subject = "Jeanpaul"
//        )).toImmutableList(),
//        isSubmit = mainState.currentExam?.isSubmit ?: false
//    )
}

@Preview
@Composable
internal actual fun ContinueCardPreview() {
}

@Preview
@Composable
internal actual fun OtherCardPreview() {
}
