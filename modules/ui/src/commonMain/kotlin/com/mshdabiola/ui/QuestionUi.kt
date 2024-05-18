package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun QuestionUi(
    modifier: Modifier = Modifier,
    number: Long,
    questionUiState: QuestionUiState,
    showAnswer: Boolean = false,
    selectedOption: Int = -1,
    onInstruction: () -> Unit = {},
    onOptionClick: (Int) -> Unit = {},
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        QuestionHeadUi(
            number = number,
            content = questionUiState.contents,
            isInstruction = questionUiState.instructionUiState != null,
            title = questionUiState.title,
            onInstruction = onInstruction,
            examID = questionUiState.examId,
            instructionTitle = questionUiState.instructionUiState?.title,
        )
        questionUiState.options?.let {
            OptionsUi(
                optionUiStates = it,
                showAnswer = showAnswer,
                selectedOption = selectedOption,
                onClick = onOptionClick,
                examId = questionUiState.id,
            )
        }
    }
}

@Composable
fun QuestionHeadUi(
    number: Long,
    title: String,
    content: ImmutableList<ItemUiState>,
    isInstruction: Boolean,
    instructionTitle: String?,
    examID: Long,
    onInstruction: () -> Unit = {},
) {
    val textColor = LocalTextStyle.current.color.copy(alpha = 0.5f)
    Card {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    "Question $number",
                    color = textColor,
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(title, color = textColor, style = MaterialTheme.typography.bodySmall)
            }

            ItemUi(content, examID = examID)

            if (isInstruction) {
                ElevatedButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onInstruction,
                ) {
                    Text(instructionTitle ?: "Read Instruction")
                }
            }
        }
    }
}
//
//@Composable
//internal expect fun QuestionHeadUiPreview()
//
//@Composable
//internal expect fun QuestionUiPreview()
