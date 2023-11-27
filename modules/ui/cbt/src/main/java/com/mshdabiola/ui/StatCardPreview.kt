package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.cbt.R
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
internal actual fun StatCardPreview() {
    StartCard(
        exams = listOf(
            ExamUiState(
                id = 1,
                subjectID = 2,
                year = 2015,
                subject = "Jeanpaul",
                isObjOnly = false,
                examTime = 400
            )
        ).toImmutableList(),
        isSubmit = false
    )
}

@Preview
@Composable
internal actual fun ContinueCardPreview() {
    ContinueCard(
        year = 2015,
        progress = 0.5f,
        timeRemain = 23,
        enabled = false,
        part = "Objective and Theory"
    )

}

@Preview
@Composable
internal actual fun OtherCardPreview() {
    OtherCard("OTherr", painter = painterResource(R.drawable.aaa))
}

