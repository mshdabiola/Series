package com.mshdabiola.series.feature.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.repository.INetworkRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Subject
import com.mshdabiola.series.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingRepository: ISettingRepository,
    private val iSubjectRepository: ISubjectRepository,
    private val networkRepository: INetworkRepository
) : ViewModel() {


    val subAndExams = iSubjectRepository
        .subjectAndExams
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val subjects = iSubjectRepository
        .subjects
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _subject = mutableStateOf("")
    val subject: State<String> = _subject

    private val _examIndex = mutableStateOf(0)
    val examIndex: State<Int> = _examIndex

    private val _examYear = mutableStateOf("")
    val examYear: State<String> = _examYear

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
                Exam(
                    subjectID = subjects.value[examIndex.value].id,
                    year = examYear.value.toLong()
                )

            )
            _examYear.value = ""
        }
    }

    fun addSubject() {
        viewModelScope.launch {
            iSubjectRepository.insertSubject(Subject(name = subject.value))
            _subject.value = ""
        }
    }

    fun onSubjectContentChange(text: String) {
        _subject.value = text
    }

    fun onExamYearContentChange(text: String) {
        try {
            text.toLong()
            _dateError.value = false
            _examYear.value = text
        } catch (e: Exception) {

            _examYear.value = text
            _dateError.value = true
        }

    }

    fun onExamIndexContentChange(index: Int) {
        _examIndex.value = index
    }
}
