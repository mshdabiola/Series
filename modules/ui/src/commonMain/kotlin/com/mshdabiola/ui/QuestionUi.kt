package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedButton
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
    title: String,
    generalPath: String,
    onInstruction: () -> Unit = {},
    onOptionClick: (Long) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        QuestionHeadUi(
            number = number,
            content = questionUiState.content,
            generalPath = generalPath,
            isInstruction = questionUiState.instructionUiState != null,
            title = title,
            onInstruction = onInstruction
        )
        OptionsUi(
            optionUiStates = questionUiState.options,
            generalPath = generalPath,
            onClick = onOptionClick
        )

    }


}

@Composable
fun QuestionHeadUi(
    number: Long,
    title: String,
    content: ImmutableList<ItemUiState>,
    isInstruction: Boolean,
    generalPath: String,
    onInstruction: () -> Unit = {}
) {
    Card {

        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Question $number")
                Text(title)
            }

            ItemUi(content, generalPath)

            if (isInstruction) {
                ElevatedButton(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onInstruction
                ) {
                    Text("Read Instruction")
                }
            }
        }

    }
}


@Composable
internal expect fun QuestionHeadUiPreview()

@Composable
internal expect fun QuestionUiPreview()
