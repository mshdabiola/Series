package com.mshdabiola.series.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.CurrentExam
import com.mshdabiola.model.data.Subject
import com.mshdabiola.series.screen.main.MainState
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.ScoreUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import com.mshdabiola.util.FileManager
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    private val iSubjectRepository: ISubjectRepository,
    iExamRepository: IExamRepository,

    ) : ViewModel() {


    private var type: ExamType = ExamType.YEAR
    private val subject = iSubjectRepository
        .getAll()
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            listOf(Subject(2, name = "English"))
        )


    private val totalTIme = 100L
    private val currentTIme = 0L


    private val _mainState =
        MutableStateFlow(MainState(listOfAllExams = emptyList<ExamUiState>().toImmutableList()))
    val mainState = _mainState.asStateFlow()


    private val _allQuestions = MutableStateFlow(emptyList<QuestionUiState>().toImmutableList())
    val allQuestions = _allQuestions.asStateFlow()

    private val _isMultiPart = MutableStateFlow(false)
    val isMultiPart= _isMultiPart.asStateFlow()

    private val _isObjPart = MutableStateFlow(false)
    val isObjPart= _isObjPart.asStateFlow()

//    private val _theQuestionsList = MutableStateFlow(emptyList<QuestionUiState>().toImmutableList())
//    val theQuestionsList = _theQuestionsList.asStateFlow()

    init {

        viewModelScope.launch(Dispatchers.IO) {
            subject
                .distinctUntilChanged { old, new -> old == new }
                .collectLatest { subjects ->
                    val sub = subjects.firstOrNull()
                    _mainState.update {
                        it.copy(title = sub?.name ?: "Subject")
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
                        it.copy(listOfAllExams = exam)
                    }
                }
        }


        viewModelScope.launch(Dispatchers.IO) {

            onContinueExam()
        }


    }

    fun startExam(examType: ExamType, yearIndex: Int, typeIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {

            type = examType

            val exam = when (type) {
                ExamType.YEAR -> {
                    mainState.value.listOfAllExams[yearIndex]
                }

                ExamType.FAST_FINGER -> {
                    mainState.value.listOfAllExams[0]
                }

                ExamType.RANDOM -> {
                    mainState.value.listOfAllExams.filter { it.isObjOnly }.random()
                }
            }
//            "Objective & Theory","Theory","Objective"

            val allQuestions =
                when (type) {
                    ExamType.YEAR,ExamType.RANDOM -> {
                        val list=getAllQuestions(exam.id)
                        when(typeIndex){
                            0->{
                                _isObjPart.update { true }
                                _isMultiPart.update { true }
                                list
                            }
                            1->{
                                _isObjPart.update { true }
                                _isMultiPart.update { false }
                                list.filter { it.isTheory.not() }
                            }
                            else->{
                                _isObjPart.update { false }
                                _isMultiPart.update { false }
                                list.filter { it.isTheory }

                            }
                        }
                    }

                    ExamType.FAST_FINGER -> {
                        _isObjPart.update { true }
                        _isMultiPart.update { false }
                        getAllQuestions(null).filter { it.isTheory.not() }
                    }

                }



            val time = when (type) {
                ExamType.RANDOM, ExamType.YEAR -> 20L
                ExamType.FAST_FINGER -> type.secondPerQuestion.toLong()
            }

            val objTime = (allQuestions.filter { it.isTheory.not() }.size) * time
            val theoryTime=(allQuestions.filter { it.isTheory }.size) * 40L
            Timber.e("tTime $theoryTime objTime $objTime")
            val chooseObj = List(allQuestions.filter { it.isTheory.not() }.size) { -1 }
            val chooseThe = List(allQuestions.filter { it.isTheory }.size) { if (it==0) 2 else -1 }

            _mainState.update {
                it.copy(
                    currentExam = exam.copy(totalTime = objTime+theoryTime, examPart =typeIndex),
                    chooseObj = chooseObj.toImmutableList(),
                    chooseThe = chooseThe.toImmutableList()
                )
            }
            _allQuestions.update {
                allQuestions.toImmutableList()
            }

        }
    }

    private suspend fun onContinueExam() {
        val currentExam1 = settingRepository.getCurrentExam()
        Timber.e("seeting exam $currentExam1")

        if (currentExam1 != null) {

            val que =
                if (currentExam1.isSubmit)
                    emptyList()
                else{
                    val list=getAllQuestions(currentExam1.id)
                    when(currentExam1.examPart){
                        0->{
                            _isObjPart.update { true }
                            _isMultiPart.update { true }
                            list
                        }
                        1->{
                            _isObjPart.update { true }
                            _isMultiPart.update { false }
                            list.filter { it.isTheory.not() }
                        }
                        else->{
                            _isObjPart.update { false }
                            _isMultiPart.update { false }
                            list.filter { it.isTheory }


                        }
                    }

                }



            val exam = mainState.value.listOfAllExams.find {
                currentExam1.id == it.id
            }

            _mainState.update {
                it.copy(
                    currentExam = exam?.copy(
                        examPart = currentExam1.examPart,
                        currentTime = currentExam1.currentTime,
                        totalTime = currentExam1.totalTime,
                        isSubmit = currentExam1.isSubmit
                    ),
                    chooseObj = currentExam1.chooseObj.toImmutableList(),
                    chooseThe = currentExam1.chooseThe.toImmutableList()
                )
            }
            _allQuestions.update {
                que.toImmutableList()
            }
        }
    }


    private suspend fun getAllQuestions(id: Long?): List<QuestionUiState> {

        val que = if (id == null)
            questionRepository.getRandom(6)
        else
            questionRepository
                .getAllWithExamId(id)

        return que
            .map { questionFulls ->
                questionFulls
                    .map {
                        it.copy(
                            options = it.options.shuffled()
                        )
                            .toQuestionUiState()
                            .copy(title = getTitle(it.examId, it.nos, it.isTheory))


                    }
            }
            .firstOrNull() ?: emptyList()
    }

    fun onOption(index: Int, optionIndex: Int) {

//
            _mainState.update {
                val choose = it.chooseObj.toMutableList()
                choose[index] = if (choose[index] == optionIndex) -1 else optionIndex
                it.copy(
                    chooseObj = choose.toImmutableList()
                )
            }

        //add and remove Choose
    }
    fun onNextTheory(index: Int) {

        if (isObjPart.value)
            return
//
        _mainState.update {
            val choose = it.chooseThe.toMutableList()
            choose[index] = 2
            it.copy(
                chooseThe = choose.toImmutableList()
            )
        }

        //add and remove Choose
    }


    fun getGeneraPath(imageType: FileManager.ImageType, examId: Long): String {
        return when (imageType) {
            FileManager.ImageType.INSTRUCTION -> "instruction/$examId"
            FileManager.ImageType.QUESTION -> "question/$examId"
        }
    }

    private suspend fun saveCurrentExam() {

        val id = mainState.value.currentExam

        if (id != null && type.save) {

            settingRepository.setCurrentExam(
                CurrentExam(
                    id = id.id,
                    currentTime = id.currentTime,
                    totalTime = id.totalTime,
                    isSubmit = id.isSubmit,
                    examPart = id.examPart,
                    chooseObj = mainState.value.chooseObj,
                    chooseThe = mainState.value.chooseThe
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
            markExam()
            saveCurrentExam()
        }
    }

    fun onFinishBack() {
        _allQuestions.update {
            emptyList<QuestionUiState>().toImmutableList()
        }
        _mainState.update {
            it.copy(currentExam = null)
        }
        viewModelScope.launch(Dispatchers.IO) {
            if (type.save) {
                settingRepository.setCurrentExam(null)
            } else {
                onContinueExam()
            }


        }
    }

    fun onRetry() {
        _allQuestions.update {
            emptyList<QuestionUiState>().toImmutableList()
        }
        mainState.value.currentExam?.let { examUiState ->
            val index = mainState.value.listOfAllExams.indexOfFirst { it.id == examUiState.id }
            Timber.e("retry index is $index")

            startExam(type, index, examUiState.examPart)
        }


    }

    private fun markExam() {
        val answerIndex = allQuestions.value
            .filter { it.isTheory.not() }
            .map { questionUiState ->
                questionUiState.options.indexOfFirst { it.isAnswer }
            }
        val choose = mainState.value.chooseObj
        val size = choose.size
        val corrent = answerIndex
            .mapIndexed { index, i ->
                choose[index] == i
            }
            .count { it }
        val inCorrect = answerIndex.size - corrent

        val complete = choose.count { it > -1 }
        val skipped = size - complete
        val compPercent = ((complete / size.toFloat()) * 100).toInt()
        val grade = when (((corrent / size.toFloat()) * 100).toInt()) {
            in 0..40 -> 'D'
            in 41..50 -> 'C'
            in 51..60 -> 'B'
            else -> 'A'
        }

        _mainState.update {
            it.copy(
                score = ScoreUiState(
                    completed = compPercent,
                    inCorrect = inCorrect,
                    skipped = skipped,
                    grade = grade,
                    correct = corrent
                )
            )
        }
    }

    private fun getTitle(examId: Long, no: Long, isTheory: Boolean): String {
        val exam = mainState.value.listOfAllExams.find { it.id == examId }

        return "Waec ${exam?.year} ${if (isTheory) "Theo" else "Obj"} Q$no"
    }

    fun togglePart() {
        _isObjPart.update {
            !it
        }
    }


}
