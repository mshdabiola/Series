package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.ScoreUiState

@Composable
fun FinishCard(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    grade: Char,
    isHide: Boolean = true,
    onShowAnswers: () -> Unit = {},
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                modifier = Modifier.size(200.dp),
                imageVector = imageVector,
                contentDescription = "cup",
                tint = MaterialTheme.colorScheme.primary,
            )
            Text(
                "Your Grade is $grade",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary,
            )
            OutlinedButton(
                onClick = onShowAnswers,
            ) {
                Text("${if (isHide) "Show" else "Hide"} correct answers")
            }
        }
    }
}
//
//@Composable
//internal expect fun FinishCardPreview()

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ScoreCard(scoreUiState: ScoreUiState) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Column {
            ScoreItem(
                heading = "Correct answers",
                content = "${scoreUiState.correct} ${if (scoreUiState.correct > 1) "Questions" else "Question"}",
            )
            ScoreItem(
                heading = "Incorrect answers",
                content = "${scoreUiState.inCorrect} ${if (scoreUiState.inCorrect > 1) "Questions" else "Question"}",
            )
        }
        Column {
            ScoreItem(heading = "Completion", content = "${scoreUiState.completed}%")
            ScoreItem(
                heading = "skipped",
                content = "${scoreUiState.skipped} ${if (scoreUiState.skipped > 1) "Questions" else "Question"}",
            )
        }
    }
}
//
//@Composable
//internal expect fun ScoreCardPreview()

@Composable
fun ScoreItem(
    heading: String,
    content: String,
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(
            heading,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.secondary,
        )
        Text(
            content,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary,
        )
    }
}
