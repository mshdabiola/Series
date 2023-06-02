package com.mshdabiola.series.feature.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.INetworkRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.series.ViewModel
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import com.mshdabiola.ui.toExam
import com.mshdabiola.ui.toSubject
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingRepository: ISettingRepository,
    private val iSubjectRepository: ISubjectRepository,
    private val iExamRepository: IExamRepository,
    private val networkRepository: INetworkRepository
) : ViewModel() {


    private val _currentSubjectId = MutableStateFlow(-1L) //-1 for all subject
    val currentSubjectId = _currentSubjectId.asStateFlow()


    private val _examUiStates = MutableStateFlow(emptyList<ExamUiState>().toImmutableList())
    val examUiStates = _examUiStates.asStateFlow()


    val subjects = iSubjectRepository
        .subjects
        .map {
            it.map { it.toUi() }
                .toImmutableList()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<SubjectUiState>().toImmutableList()
        )

    private val _subject = mutableStateOf(SubjectUiState(name = ""))
    val subject: State<SubjectUiState> = _subject

    private val _examIndex = mutableStateOf(ExamUiState(subjectID = -1L, year = -1L, subject = ""))
    val examIndex: State<ExamUiState> = _examIndex


    private val _dateError = mutableStateOf(false)
    val dateError: State<Boolean> = _dateError


    init {

        viewModelScope.launch {
            currentSubjectId.collectLatest { id ->
                if (id == -1L) {
                    iExamRepository
                        .allExams
                        .collectLatest { list ->
                            _examUiStates.update {
                                list
                                    .map { it.toUi() }
                                    .toImmutableList()
                            }
                        }

                } else {
                    iExamRepository.getExamBySubjectId(id)
                        .collectLatest { list ->
                            _examUiStates.update {
                                list
                                    .map { it.toUi() }
                                    .toImmutableList()
                            }
                        }


                }
            }
        }

        viewModelScope.launch {
//            multiplatformSettings.name.collectLatest {
//                print(it)
//            }
        }
        viewModelScope.launch {
//            settingRepository.setName("Ademola")
        }
        viewModelScope.launch(Dispatchers.IO) {
            try {
                networkRepository.gotoGoogle()
            } catch (e: Exception) {
                //   e.printStackTrace()
            }


            settingRepository.dummy
                .collectLatest {
                    println(it)
                }

        }

//        viewModelScope.launch(Dispatchers.IO) {
//            settingRepository.setDummy(DummySetting("abiola", "female"))
//        }female
    }


    fun onSubject(index: Long) {
        _currentSubjectId.update {
            index
        }

    }

    fun addExam() {
        viewModelScope.launch {
            iExamRepository.insertExam(
                examIndex.value.toExam()
            )
            _examIndex.value = examIndex.value.copy(id = -1, year = -1)
        }
    }

    fun addSubject() {
        viewModelScope.launch {
            iSubjectRepository.insertSubject(subject.value.toSubject())
            _subject.value = SubjectUiState(name = "")
        }
    }

    fun onSubjectContentChange(text: String) {
        _subject.value = subject.value.copy(name = text)
    }

    fun onExamYearContentChange(text: String) {
        try {

            _dateError.value = false
            _examIndex.value = examIndex.value.copy(year = text.toLong())
        } catch (e: Exception) {
            _dateError.value = true
        }

    }

    fun onSubjectIdChange(id: Long) {
        subjects.value.find { it.id == id }?.let {
            _examIndex.value = ExamUiState(subjectID = -1L, year = -1L, subject = "")
        }
    }

    fun onDeleteExam(id: Long) {
        viewModelScope.launch {

        }
    }

    fun onUpdateExam(id: Long) {
        examUiStates.value.find { it.id == id }?.let {
            _examIndex.value = it
        }
    }

    fun updateSubject(id: Long) {

        subjects.value.find { it.id == id }?.let {
            _subject.value = it.copy(focus = true)
        }
    }
}
