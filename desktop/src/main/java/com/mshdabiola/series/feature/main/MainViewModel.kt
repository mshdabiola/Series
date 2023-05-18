package com.mshdabiola.series.feature.main

import com.mshdabiola.data.repository.INetworkRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Subject
import com.mshdabiola.series.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(
    private val settingRepository: ISettingRepository,
    private val iSubjectRepository: ISubjectRepository,
    private val networkRepository: INetworkRepository
) : ViewModel() {
    companion object {
        const val INIT_WELCOME_MSG = "Hello World! Skeleton"
    }

    private val _welcomeText = MutableStateFlow(INIT_WELCOME_MSG)
    val welcomeText: StateFlow<String> = _welcomeText

    val subAndExams = iSubjectRepository
        .subjectAndExams
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    val subjcts = iSubjectRepository
        .subjects
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )


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

    fun addExam(exam: Exam) {
        viewModelScope.launch {
            iSubjectRepository.insertExam(exam)
        }
    }

    fun addSubject(subject: Subject) {
        viewModelScope.launch {
            iSubjectRepository.insertSubject(subject)
        }
    }
}
