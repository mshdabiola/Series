package com.mshdabiola.questionscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import timber.log.Timber


class QuestionViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {
    private val questions =
        listOf(
            QuestionUiState(
                id = 1,
                nos = 1,
                content = listOf(
                    ItemUiState(content = "What is your name")
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1, nos = 1, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 3, nos = 3, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    )
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList()
                )
            ),
            QuestionUiState(
                id = 1,
                nos = 1,
                content = listOf(
                    ItemUiState(content = "What is your name")
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1, nos = 1, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = true
                    ),
                    OptionUiState(
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 3, nos = 3, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    )
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList()
                )
            ),
            QuestionUiState(
                id = 1,
                nos = 1,
                content = listOf(
                    ItemUiState(content = "What is your name")
                )
                    .toImmutableList(),
                options = listOf(
                    OptionUiState(
                        id = 1, nos = 1, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = true
                    ),
                    OptionUiState(
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 3, nos = 3, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    ),
                    OptionUiState(
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false, choose = false
                    )
                ).toImmutableList(),
                instructionUiState = InstructionUiState(
                    id = 1,
                    examId = 3,
                    title = "What",
                    content = listOf(ItemUiState()).toImmutableList()
                )
            )
        ).toImmutableList()

    private val _questionsList = MutableStateFlow(questions)
    val questionsList = _questionsList.asStateFlow()

    init {
        Timber.e("init question first ")
    }

    fun onOption(index: Int, optionIndex: Int) {
        val question = questionsList.value.toMutableList()
        var optionList = question[index]
            .options.toMutableList()
        val lastChoose=optionList.indexOfFirst { it.choose }
        optionList = optionList.map {
            it.copy(choose = false)
        }.toMutableList()
        optionList[optionIndex] = optionList[optionIndex]
            .copy(choose = lastChoose != optionIndex)
        question[index] = question[index].copy(options = optionList.toImmutableList())
        _questionsList.update {
            question.toImmutableList()
        }

    }
}
