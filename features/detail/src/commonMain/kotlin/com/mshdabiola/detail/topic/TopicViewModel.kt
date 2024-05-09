package com.mshdabiola.detail.topic

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.Converter
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.TopicInputUiState
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.toTopic
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class TopicViewModel (
    private val subjectId : Long,
    private val topicRepository: ITopicRepository,

    ): ViewModel() {

    private val converter = Converter()

    init {
        viewModelScope.launch {
            topicRepository
                .getAllBySubject(subjectId)
                .map { it.map { it.toUi() }.toImmutableList() }
                .collectLatest {
                    topicUiStates.value = it

                }
        }
    }
    // topic

    val topicUiStates = mutableStateOf(emptyList<TopicUiState>().toImmutableList())
    private val _topicUiState = mutableStateOf(TopicUiState(subjectId = subjectId, name = ""))
    val topicUiState: State<TopicUiState> = _topicUiState


    private val _topicInputUiState = mutableStateOf(TopicInputUiState("", false))
    val topicInputUiState: State<TopicInputUiState> = _topicInputUiState

    // topic logic


    init {

        viewModelScope.launch {
            topicRepository
                .getAllBySubject(subjectId)
                .map { it.map { it.toUi() }.toImmutableList() }
                .collectLatest {
                    topicUiStates.value = it

                }
        }
    }
    fun onAddTopic() {
        viewModelScope.launch {
            topicRepository.upsert(topicUiState.value.toTopic())
            _topicUiState.value = TopicUiState(subjectId = subjectId, name = "")
        }
    }

    fun onTopicChange(text: String) {
        _topicUiState.value = topicUiState.value.copy(name = text)
    }

    fun onDeleteTopic(id: Long) {
        viewModelScope.launch {
            topicRepository.delete(id)
        }
    }

    fun onUpdateTopic(id: Long) {
        topicUiStates.value.find { it.id == id }?.let {
            _topicUiState.value = it.copy(focus = true)
        }
    }


    fun onAddTopicFromInput() {
        viewModelScope.launch {
            try {
                val list =
                   converter.textToTopic(
                        path = topicInputUiState.value.content,
                        subjectId = subjectId,
                    )

                log(list.joinToString())
                launch { topicRepository.insertAll(list) }
                _topicInputUiState.value =
                    topicInputUiState.value.copy(content = "", isError = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _topicInputUiState.value = topicInputUiState.value.copy(isError = true)
            }
        }
    }


    fun onTopicInputChanged(text: String) {
        _topicInputUiState.value = topicInputUiState.value.copy(content = text)
    }

    private fun log(msg: String) {
//        co.touchlab.kermit.Logger.e(msg)
    }
}