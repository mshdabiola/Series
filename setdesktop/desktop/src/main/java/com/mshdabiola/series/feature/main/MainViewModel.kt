package com.mshdabiola.series.feature.main

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.common.ExInPort
import com.mshdabiola.data.repository.inter.IExInPortRepository
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.INetworkRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
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
    private val networkRepository: INetworkRepository,
    private val iExInPortRepository: IExInPortRepository,
    private val exInPort: ExInPort
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

    private val _exam = mutableStateOf(ExamUiState(subjectID = -1L, year = -1L, subject = ""))
    val exam: State<ExamUiState> = _exam


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
                exam.value.toExam()
            )
            _exam.value = exam.value.copy(id = -1, year = -1)
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

    fun onSubjectIdChange(id: Long) {
        subjects.value.find { it.id == id }?.let {
            _exam.value = exam.value.copy(subjectID = it.id, subject = it.name)
        }
    }

    fun onDeleteExam(id: Long) {
        viewModelScope.launch {

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

    fun onExport(path: String) {
        viewModelScope.launch {
            iExInPortRepository.export(
                coroutineScope = this,
                subjectId = currentSubjectId.value
            ) { subjects: List<Subject>, exams: List<Exam>, questions: List<Question>, options: List<Option>, instructions: List<Instruction>, topics: List<Topic> ->

                viewModelScope.launch {
                    launch { exInPort.copyImage(path, subjects) }
                    launch {
                        exInPort.export(
                            subjects,
                            path,
                            ExInPort.subject
                        )
                    }

                }
                viewModelScope.launch {
                    exInPort.export(
                        exams,
                        path,
                        ExInPort.exam
                    )
                }
                viewModelScope.launch {
                    exInPort.export(
                        questions,
                        path,
                        ExInPort.question
                    )
                }
                viewModelScope.launch {
                    exInPort.export(
                        options,
                        path,
                        ExInPort.option
                    )
                }
                viewModelScope.launch {
                    exInPort.export(
                        instructions,
                        path,
                        ExInPort.instruction
                    )
                }
                viewModelScope.launch {
                    exInPort.export(
                        topics,
                        path,
                        ExInPort.topic
                    )
                }

//
//                println(subjects)
//                println(exams)
//                println(questions)
//                println(options)
//                println(instructions)
//                println(topics)

            }
        }

    }
}
