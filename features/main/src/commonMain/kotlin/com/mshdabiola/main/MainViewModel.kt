/*
 *abiola 2022
 */

package com.mshdabiola.main

import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.MainState
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import com.mshdabiola.ui.toExam
import com.mshdabiola.ui.toSubject
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel constructor(
    private val iSubjectRepository: ISubjectRepository,
    private val iExamRepository: IExaminationRepository,

    ) : ViewModel() {

    private val _mainState = MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch {
            iSubjectRepository
                .getAll()
                .mapNotNull { subjectList -> subjectList.map { it.toUi() }.toImmutableList() }
                .collectLatest { subjects ->
                    _mainState.update {
                        it.copy(subjects = subjects)
                    }
                }
        }

        viewModelScope.launch {
            iExamRepository
                .getAll()
                .mapNotNull { subjectList -> subjectList.map { it.toUi() }.toImmutableList() }
                .collectLatest { exams ->
                    _mainState.update {
                        it.copy(examinations = exams)
                    }
                }
        }
    }

    fun onSubject(index: Long) {

        _mainState.update {
            it.copy(currentSubjectId = index)
        }
    }

    fun addExam() {
        viewModelScope.launch {
            val examUiState = mainState.value.examination

            iExamRepository.upsert(examUiState.toExam())
            _mainState.update { it.copy(examination = ExamUiState()) }
        }
    }

    fun addSubject() {
        viewModelScope.launch {
            iSubjectRepository.upsert(mainState.value.subject.toSubject())
            _mainState.update {
                it.copy(subject = SubjectUiState(name = ""))
            }
        }
    }

    fun onSubjectContentChange(text: String) {
        _mainState.update {
            it.copy(subject = it.subject.copy(name = text))
        }
    }

    fun onExamYearContentChange(text: String) {

        _mainState.update {
            it.copy(examination = it.examination.copy(year = text.toLong()))
        }
//        try {
//            _dateError.value = false
//            _exam.value = exam.value?.copy(year = text.toLong())
//        } catch (e: Exception) {
//            _dateError.value = true
//        }
    }

    fun onExamDurationContentChange(text: String) {

        _mainState.update {
            it.copy(examination = it.examination.copy(duration = text.toLong()))
        }
//        try {
//            _exam.value = exam.value?.copy(duration = text.toLongOrNull() ?: -1)
//        } catch (e: Exception) {
//        }
    }

    fun onSubjectIdChange(id: Long) {
        _mainState.update {
            it.copy(examination = it.examination.copy(subject = mainState.value.subjects[id.toInt()]))
        }
//        subjects.value.find { it.id == id }?.let {
//            _exam.value = exam.value?.copy(subject = it)
//        }
    }

    fun onDeleteExam(id: Long) {
        viewModelScope.launch {
            iExamRepository.delete(id)
        }
    }

    fun onUpdateExam(id: Long) {
        _mainState.update {
            it.copy(examination = mainState.value.examinations[id.toInt()])
        }
//        examUiStates.value.find { it.id == id }?.let {
//            _exam.value = it
//        }
    }

    fun updateSubject(id: Long) {
        _mainState.update {
            it.copy(subject = mainState.value.subjects[id.toInt()].copy(focus = true))
        }
//        subjects.value.find { it.id == id }?.let {
//            _subject.value = it.copy(focus = true)
//        }
    }

    fun onExport(path: String, name: String, key: String, version: Int) {
        viewModelScope.launch {
            val ids = mainState.value.examinations
                .filter { it.isSelected }
                .map { it.id }

            iExamRepository.export(ids, path, name, version, key)
            deselectAll()
        }
    }

    fun toggleSelectMode() {
        _mainState.update {
            it.copy(isSelectMode = !it.isSelectMode)
        }
//        _isSelectMode.value = !isSelectMode.value
    }

    fun toggleSelect(index: Long) {
        val exams = mainState.value.examinations.toMutableList()
        val examIndex = exams.indexOfFirst { it.id == index }
        if (examIndex == -1) {
            return
        }
        val exam = exams[examIndex]
        exams[examIndex] = exam.copy(isSelected = !exam.isSelected)
        if (!mainState.value.isSelectMode && !exam.isSelected) {
            toggleSelectMode()
            // _isSelectMode.value = true
        }

        _mainState.update {
            it.copy(examinations = exams.toImmutableList())
        }
        //  _examUiStates.value = exams.toImmutableList()
    }

    fun deselectAll() {
        val exams = mainState.value.examinations
            .map { it.copy(isSelected = false) }
            .toImmutableList()

        _mainState.update {
            it.copy(
                isSelectMode = false,
                examinations = exams
            )
        }

    }

    fun selectAll() {
        val exams = mainState.value.examinations

            .map { it.copy(isSelected = true) }
            .toImmutableList()

        _mainState.update {
            it.copy(
                examinations = exams
            )
        }
    }

    fun selectAllSubject() {
        val exams = mainState.value
            .examinations
            .map {
                if (it.subject.id == mainState.value.currentSubjectId) {
                    it.copy(isSelected = true)
                } else {
                    it.copy(isSelected = false)
                }
            }
            .toImmutableList()

        _mainState.update {
            it.copy(
                examinations = exams
            )
        }
//
//        _examUiStates.value = exams
    }

    fun deleteSelected() {
        val exams = mainState
            .value
            .examinations
            .filter { it.isSelected }
            .toImmutableList()
        exams.forEach {
            onDeleteExam(it.id)
        }
        _mainState.update {
            it.copy(
                isSelectMode = false,
            )
        }
//        _isSelectMode.value = false
    }

}
