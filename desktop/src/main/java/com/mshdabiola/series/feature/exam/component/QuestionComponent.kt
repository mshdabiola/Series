package com.mshdabiola.series.ui.feature.exam.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.series.ui.feature.exam.state.OptionsUiState
import com.mshdabiola.series.ui.feature.exam.state.QuestionUiState


@Composable
fun QuestionUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState
) {

    Column(modifier) {
        Text(questionUiState.content)

        LazyVerticalGrid(columns = GridCells.Fixed(2)) {
            items(questionUiState.options, key = { it.id }) {

                Text(it.content)

            }
        }
    }
}

@Preview
@Composable
fun QuestionUiPreview() {

    val questionUiState = QuestionUiState(
        id = 1,
        content = "Baltazar",
        options = listOf(
            OptionsUiState(id = 1, content = "Leland", isAnswer = false),
            OptionsUiState(id = 2, content = "Leland", isAnswer = false),
            OptionsUiState(id = 3, content = "Leland", isAnswer = false)
        )
    )
    QuestionUi(questionUiState = questionUiState)

}