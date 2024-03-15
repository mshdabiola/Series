package com.mshdabiola.series.screen.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import com.mshdabiola.ui.toExam
import com.mshdabiola.ui.toSubject
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
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
) : ViewModel() {

    private val _currentSubjectId = MutableStateFlow(-1L) // -1 for all subject
    val currentSubjectId = _currentSubjectId.asStateFlow()

    private val _examUiStates = MutableStateFlow(emptyList<ExamUiState>().toImmutableList())
    val examUiStates = _examUiStates.asStateFlow()

    val subjects = iSubjectRepository
        .getAll()
        .map {
            it.map { it.toUi() }
                .toImmutableList()
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<SubjectUiState>().toImmutableList(),
        )

    private val _subject = mutableStateOf(SubjectUiState(name = ""))
    val subject: State<SubjectUiState> = _subject

    private val _exam =
        mutableStateOf(
            ExamUiState(
                subjectID = -1L,
                year = -1L,
                subject = "",
                isObjOnly = true,
                examTime = 15,
            ),
        )
    val exam: State<ExamUiState> = _exam

    private val _dateError = mutableStateOf(false)
    val dateError: State<Boolean> = _dateError

    private val _isSelectMode = mutableStateOf(false)
    val isSelectMode: State<Boolean> = _isSelectMode

    init {

        viewModelScope.launch {
            currentSubjectId.collectLatest { id ->
                if (id == -1L) {
                    iExamRepository
                        .getAllWithSub()
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
    }

    fun onSubject(index: Long) {
        _currentSubjectId.update {
            index
        }
    }

    fun addExam() {
        viewModelScope.launch {
            iExamRepository.insertExam(
                exam.value.toExam(),
            )
            _exam.value = exam.value.copy(id = -1, year = -1, subject = "")
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
            _exam.value = exam.value.copy(year = text.toLong())
        } catch (e: Exception) {
            _dateError.value = true
        }
    }

    fun onExamDurationContentChange(text: String) {
        try {
            _exam.value = exam.value.copy(examTime = text.toLongOrNull() ?: -1)
        } catch (e: Exception) {
        }
    }

    fun onSubjectIdChange(id: Long) {
        subjects.value.find { it.id == id }?.let {
            _exam.value = exam.value.copy(subjectID = it.id, subject = it.name)
        }
    }

    fun onDeleteExam(id: Long) {
        viewModelScope.launch {
            iExamRepository.deleteExam(id)
        }
    }

    fun onUpdateExam(id: Long) {
        examUiStates.value.find { it.id == id }?.let {
            _exam.value = it
        }
    }

    fun updateSubject(id: Long) {
        subjects.value.find { it.id == id }?.let {
            _subject.value = it.copy(focus = true)
        }
    }

    fun onExport(path: String, name: String, key: String, version: Int) {
        viewModelScope.launch {
            val ids = examUiStates
                .value
                .filter { it.isSelected }
                .map { it.id }
            iExamRepository.export(ids, path, name, version, key)
            deselectAll()
        }
    }

    fun toggleSelectMode() {
        _isSelectMode.value = !isSelectMode.value
    }

    fun toggleSelect(index: Long) {
        val exams = examUiStates.value.toMutableList()
        val examIndex = exams.indexOfFirst { it.id == index }
        if (examIndex == -1) {
            return
        }
        val exam = exams[examIndex]
        exams[examIndex] = exam.copy(isSelected = !exam.isSelected)
        if (!isSelectMode.value && !exam.isSelected) {
            _isSelectMode.value = true
        }

        _examUiStates.value = exams.toImmutableList()
    }

    fun deselectAll() {
        val exams = examUiStates
            .value
            .map { it.copy(isSelected = false) }
            .toImmutableList()

        _examUiStates.value = exams
        _isSelectMode.value = false
    }

    fun selectAll() {
        val exams = examUiStates
            .value
            .map { it.copy(isSelected = true) }
            .toImmutableList()

        _examUiStates.value = exams
    }

    fun selectAllSubject() {
        val exams = examUiStates
            .value
            .map {
                if (it.subjectID == currentSubjectId.value) {
                    it.copy(isSelected = true)
                } else {
                    it.copy(isSelected = false)
                }
            }
            .toImmutableList()

        _examUiStates.value = exams
    }

    fun deleteSelected() {
        val exams = examUiStates
            .value
            .filter { it.isSelected }
            .toImmutableList()
        exams.forEach {
            onDeleteExam(it.id)
        }
        _isSelectMode.value = false
    }
}
