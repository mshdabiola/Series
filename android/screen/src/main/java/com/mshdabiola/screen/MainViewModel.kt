package com.mshdabiola.screen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.CurrentExam
import com.mshdabiola.model.data.Subject
import com.mshdabiola.screen.main.MainState
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import com.mshdabiola.util.FileManager
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    private val iSubjectRepository: ISubjectRepository,
    private val iExamRepository: IExamRepository,

    ) : ViewModel() {


    val subject = iSubjectRepository
        .getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            listOf(Subject(2, name = "English"))
        )


    private var chooseList: MutableMap<Int, Int> = HashMap()


    private val _mainState =
        MutableStateFlow(MainState(exams = emptyList<ExamUiState>().toImmutableList()))
    val mainState = _mainState.asStateFlow()


    private val _questionsList = MutableStateFlow(emptyList<QuestionUiState>().toImmutableList())
    val questionsList = _questionsList.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            subject
                .distinctUntilChanged { old, new -> old == new }
                .collectLatest {
                    val sub = it.first()
                    _mainState.update {
                        it.copy(title = sub.name)
                    }


                }
        }

        viewModelScope.launch(Dispatchers.IO) {
            iExamRepository
                .getAllWithSub()
                .map { examWithSubs ->
                    examWithSubs
                        .map { it.toUi() }
                        .toImmutableList()
                }
                .collectLatest { exam ->
                    _mainState.update {
                        it.copy(exams = exam)
                    }
                }
        }


        viewModelScope.launch(Dispatchers.IO) {
            val currentExam1 = settingRepository.getCurrentExam()
            Timber.e("seeting exam $currentExam1")
            if (currentExam1 != null) {
                val exam = mainState.value.exams.find {
                    currentExam1.id == it.id
                }
                Timber.e("exam $exam")
                exam?.let { old ->
                    _mainState.update {
                        it.copy(
                            currentExam = exam.copy(
                                currentTime = currentExam1.currentTime,
                                totalTime = currentExam1.totalTime,
                                progress = currentExam1.progress,
                                isSubmit = currentExam1.isSubmit
                            ),
                        )
                    }
                    chooseList = currentExam1.choose.associate {
                        it
                    }.toMutableMap()
                    onContinueExam()
                }
            }
        }


    }

    fun startExam(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(1000)
            val exam = mainState.value.exams[index]
            _mainState.update {
                it.copy(
                    currentExam = exam.copy(
                        id = 1,
                        currentTime = 0,
                        totalTime = 40,
                        isSubmit = false,
                        progress = 0f
                    )
                )
            }

            addQuestions(1)
        }
    }

    private suspend fun onContinueExam() {
        addQuestions(1)
        addChoose()
    }

    private suspend fun addQuestions(examId: Long) {
        val que = questionRepository
            .getAllWithExamId(1)
            .map { questionFulls ->
                questionFulls.map {
                    it
                        .toQuestionUiState()

                }.toImmutableList()
            }
            .firstOrNull()


        if (que != null) {
            _questionsList.update {
                que
            }
        }


    }

    private var chooseJob: Job? = null

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

        //add and remove Choose
        chooseJob?.cancel()
        chooseJob = viewModelScope.launch(Dispatchers.Default) {
            if (lastChoose != optionIndex) {
                //add
                chooseList[index] = optionIndex
            } else {
                chooseList.remove(index)
            }
            val progress=chooseList.size/questionsList.value.size.toFloat()

            _mainState.update {
                it.copy(currentExam = it.currentExam?.copy(progress=progress))
            }

        }

    }

    fun getGeneraPath(imageType: FileManager.ImageType): String {
        return "image"//fileManager.getGeneraPath(subjectId, examId, imageType)
    }

    private suspend fun saveCurrentExam() {

        val id = mainState.value.currentExam

        if (id != null) {
            val choose = chooseList.toList()

            settingRepository.setCurrentExam(
                CurrentExam(
                    id = id.id,
                    currentTime = id.currentTime,
                    totalTime = id.totalTime,
                    isSubmit = id.isSubmit,
                    progress = id.progress,
                    choose = choose
                )
            )

        }


    }

    private fun addChoose() {
        viewModelScope.launch(Dispatchers.Default) {
            val question = questionsList.value.toMutableList()
            chooseList.forEach { entry ->
                val index = entry.key
                val optionIndex = entry.value
                var optionList = question[index]
                    .options.toMutableList()
                val lastChoose = optionList.indexOfFirst { it.choose }
                optionList = optionList.map {
                    it.copy(choose = false)
                }.toMutableList()
                optionList[optionIndex] = optionList[optionIndex]
                    .copy(choose = lastChoose != optionIndex)
                question[index] = question[index].copy(options = optionList.toImmutableList())
            }

            _questionsList.update {
                question.toImmutableList()
            }

        }

    }

    private var saveJob: Job? = null
    fun onTimeChanged(time: Long) {
        _mainState.update {
            it.copy(currentExam = it.currentExam?.copy(currentTime = time))
        }
        saveJob?.cancel()
        saveJob = viewModelScope.launch {
            saveCurrentExam()
        }
    }


    fun onSubmit() {
        viewModelScope.launch(Dispatchers.IO) {
            settingRepository.setCurrentExam(null)
            _mainState.update {
                it.copy(currentExam = it.currentExam?.copy(isSubmit = true))
            }
        }
    }


}
