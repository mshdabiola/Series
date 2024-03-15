package com.mshdabiola.series.screen.finish

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import com.mshdabiola.series.screen.main.MainState
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
actual fun FinishScreenPreview() {
    val questions =
        listOf(
            QuestionUiState(
                id = 1,
                nos = 1,
                examId = 0,
                content = listOf(
                    ItemUiState(content = "What is your name"),
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1,
                        nos = 1,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 2,
                        nos = 2,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 3,
                        nos = 3,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 4,
                        nos = 4,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList(),
                ),
            ),
            QuestionUiState(
                id = 1,
                nos = 1,
                examId = 0,
                content = listOf(
                    ItemUiState(content = "What is your name"),
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1,
                        nos = 1,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 2,
                        nos = 2,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 3,
                        nos = 3,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 4,
                        nos = 4,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList(),
                ),
            ),
            QuestionUiState(
                id = 1,
                nos = 1,
                examId = 0,
                content = listOf(
                    ItemUiState(content = "What is your name"),
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1,
                        nos = 1,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 2,
                        nos = 2,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 3,
                        nos = 3,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                    OptionUiState(
                        id = 4,
                        nos = 4,
                        content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            ),
                        ).toImmutableList(),
                        isAnswer = false,
                    ),
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList(),
                ),
            ),
        ).toImmutableList()
    FinishScreen(

        mainState = MainState(
            title = "Jade",
            currentExam = null,
            listOfAllExams = emptyList<ExamUiState>().toImmutableList(),
        ),
    )
}
