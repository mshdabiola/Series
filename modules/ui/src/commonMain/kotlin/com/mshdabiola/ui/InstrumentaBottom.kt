package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.InstructionUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllQuestionBottomSheet(
    show: Boolean,
    chooses: ImmutableList<Int>,
    currentNumber: Int,
    onChooseClick: (Int) -> Unit = {},
    onDismissRequest: () -> Unit = {},
) {
    if (show) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(text = "All questions")

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Adaptive(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        items(count = chooses.size, key = { it }) {
                            QuestionNumberButton(
                                number = it,
                                isChoose = chooses[it] > -1,
                                isCurrent = it == currentNumber,
                                onClick = { onChooseClick(it) },
                            )
                        }
                    },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionBottomSheet(
    instructionUiState: InstructionUiState?,
    onDismissRequest: () -> Unit = {},
) {
    if (instructionUiState != null) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                ItemUi(items = instructionUiState.content, examID = instructionUiState.examId)
            }
        }
    }
}
