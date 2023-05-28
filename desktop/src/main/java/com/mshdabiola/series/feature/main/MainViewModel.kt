package com.mshdabiola.series.feature.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingRepository: ISettingRepository,
    private val iSubjectRepository: ISubjectRepository,
    private val networkRepository: INetworkRepository
) : ViewModel() {


    val subAndExams = iSubjectRepository
        .subjectAndExams
        .map { it.map { it.toUi() }.toImmutableList() }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList<ExamUiState>().toImmutableList()
        )

    val subjects = iSubjectRepository
        .subjects
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _subject = mutableStateOf(SubjectUiState(name=""))
    val subject: State<SubjectUiState> = _subject

    private val _examIndex = mutableStateOf(ExamUiState(subjectID = -1L, year = -1L, subject = ""))
    val examIndex: State<ExamUiState> = _examIndex


    private val _dateError = mutableStateOf(false)
    val dateError: State<Boolean> = _dateError


    init {


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

    fun addExam() {
        viewModelScope.launch {
            iSubjectRepository.insertExam(
              examIndex.value.toExam()
            )
           _examIndex.value= examIndex.value.copy(id=-1,year = -1)
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
            _examIndex.value=examIndex.value.copy(year = text.toLong())
        } catch (e: Exception) {
            _dateError.value = true
        }

    }

    fun onSubjectIdChange(id: Long) {
        subjects.value.find { it.id==id }?.let {
            _examIndex.value=examIndex.value.copy(subjectID = it.id, subject = it.name)
        }
    }

    fun onDeleteExam(id: Long){
        viewModelScope.launch {

        }
    }
    fun onUpdateExam(id: Long){
        subAndExams.value.find { it.id==id }?.let {
            _examIndex.value=it
        }
    }
}
