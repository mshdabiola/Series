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


    private val totalTIme = 100L
    private val currentTIme = 0L


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
                                isSubmit = currentExam1.isSubmit
                            ),
                            choose = currentExam1.choose.toImmutableList()
                        )
                    }

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
            _mainState.update {
                it.copy(choose = MutableList(que.size) { -1 }.toImmutableList())
            }
        }


    }


    fun onOption(index: Int, optionIndex: Int) {
//
        _mainState.update {
            val choose = it.choose.toMutableList()
            choose[index] = if (choose[index] == optionIndex) -1 else optionIndex
            it.copy(
                choose = choose.toImmutableList()
            )
        }

        //add and remove Choose


    }

    fun getGeneraPath(imageType: FileManager.ImageType): String {
        return "image"//fileManager.getGeneraPath(subjectId, examId, imageType)
    }

    private suspend fun saveCurrentExam() {

        val id = mainState.value.currentExam

        if (id != null) {

            settingRepository.setCurrentExam(
                CurrentExam(
                    id = id.id,
                    currentTime = id.currentTime,
                    totalTime = id.totalTime,
                    isSubmit = id.isSubmit,
                    choose = mainState.value.choose
                )
            )

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
            _mainState.update {
                it.copy(
                    currentExam = it.currentExam?.copy(
                        isSubmit = true,
                        currentTime = currentTIme,
                        totalTime = totalTIme
                    )
                )

            }
            saveCurrentExam()
        }
    }

    fun onFinishBack() {
        _questionsList.update {
            emptyList<QuestionUiState>().toImmutableList()
        }
        _mainState.update {
            it.copy(currentExam = null)
        }
        viewModelScope.launch(Dispatchers.IO){
            settingRepository.setCurrentExam(null)
        }
    }

    fun onRetry() {
        _questionsList.update {
            emptyList<QuestionUiState>().toImmutableList()
        }
        mainState.value.currentExam?.id?.let { id ->
            val index = mainState.value.exams.indexOfFirst { it.id == id }
            startExam(index)
        }


    }


}
