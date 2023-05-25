package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.model.data.Type
import com.mshdabiola.series.ViewModel
import com.mshdabiola.series.feature.exam.state.ItemUi
import com.mshdabiola.series.feature.exam.state.OptionsUiState
import com.mshdabiola.series.feature.exam.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ExamViewModel(private val id: Long) : ViewModel() {


    var question =
        mutableStateOf(
            QuestionUiState(
                id = 1,
                content = listOf(
                    ItemUi(isEditMode = true),
                ).toImmutableList(),
                options = listOf(
                    OptionsUiState(
                        id = 1,
                        content = listOf(ItemUi(isEditMode = true)).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 2,
                        content = listOf(
                            ItemUi(isEditMode = true)
                        ).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 3, content = listOf(ItemUi(isEditMode = true)).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 4,
                        content = listOf(
                            ItemUi(isEditMode = true)
                        ).toImmutableList(),
                        isAnswer = false
                    )
                ).toImmutableList(),
                editMode = true
            )
        )

    private val _questions = MutableStateFlow(emptyList<QuestionUiState>().toImmutableList())
    val questions = _questions.asStateFlow()

    fun onAddQuestion() {
        val question2 = question.value

        _questions.update { questionUiStates ->
            val list = questionUiStates.toMutableList()
            list.add(
                question2.copy(
                    content = question2.content.map { it.copy(isEditMode = false) }
                        .toImmutableList(),
                    options = question2.options.map { optionsUiState ->
                        optionsUiState.copy(content = optionsUiState.content.map {
                            it.copy(isEditMode = false)
                        }.toImmutableList())
                    }.toImmutableList(),
                    editMode = false
                )
            )
            list.toImmutableList()
        }
        question.value = getEmptyQuestion()
    }

    private fun getEmptyQuestion(): QuestionUiState {
        return QuestionUiState(
            id = 1,
            content = listOf(
                ItemUi(isEditMode = true)
            ).toImmutableList(),
            options = listOf(
                OptionsUiState(
                    id = 1,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 2,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 3, content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 4,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
            editMode = true
        )
    }


    fun addUP(questionIndex: Int, index: Int) {
        editContent(questionIndex, index) {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUi(isEditMode = true))
        }
    }

    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex, index) {

            it.add(index + 1, ItemUi(isEditMode = true))
        }
    }

    fun moveUP(questionIndex: Int, index: Int) {
        if (index == 0)
            return
        editContent(questionIndex, index) {

            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

        }
    }

    fun moveDown(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }

        }
    }

    fun edit(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)
        }
    }

    fun delete(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            it.removeAt(index)
        }
    }

    fun changeType(questionIndex: Int, index: Int, type: Type) {
        editContent(questionIndex, index) {
            var item = it[index]

            it[index] = ItemUi(isEditMode = true, type = type)
        }
    }

    fun onTextChange(questionIndex: Int, index: Int, text: String) {
        editContent(questionIndex, index) {
            var item = it[index]

            it[index] = item.copy(content = text)
        }
    }

    private fun editContent(
        questionIndex: Int, index: Int,
        items: (MutableList<ItemUi>) -> Unit
    ) {
        var quest = question.value

        if (questionIndex == -1) {
            val qItem = quest.content.toMutableList()
            items(qItem)
            quest = quest.copy(content = qItem.toImmutableList())

        } else {
            val options = quest.options.toMutableList()
            var option = options[questionIndex]
            val qItem = option.content.toMutableList()
            items(qItem)
            option = option.copy(content = qItem.toImmutableList())
            options[questionIndex] = option

            quest = quest.copy(options = options.toImmutableList())


        }
        question.value = quest

    }


}