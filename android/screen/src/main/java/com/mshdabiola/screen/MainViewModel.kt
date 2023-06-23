package com.mshdabiola.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.inter.IModelRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.toQuestionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
    private val questionRepository: IQuestionRepository
) : ViewModel() {



    private val id =1L



    private val _questionsList = MutableStateFlow(emptyList<QuestionUiState>().toImmutableList())
    val questionsList = _questionsList.asStateFlow()

    init {
        Timber.e("init")
        viewModelScope.launch {
            questionRepository.getAllWithExamId(id)
                .map {
                    it
                        .map { it.toQuestionUiState() }
                        .toImmutableList()
                }
                .collectLatest { questionUiStates ->
                    Timber.e("new ${questionUiStates.joinToString ()}")
                    _questionsList.update {
                        questionUiStates
                    }
                }
        }

    }


    fun onOption(index: Int, optionIndex: Int) {
        val question = questionsList.value.toMutableList()
        var optionList = question[index]
            .options.toMutableList()
        val lastChoose = optionList.indexOfFirst { it.choose }
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
