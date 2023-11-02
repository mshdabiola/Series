package com.mshdabiola.series.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.series.screen.main.MainState
import com.mshdabiola.series.screen.main.Section
//import com.mshdabiola.ui.screen.ExamType
//import com.mshdabiola.ui.screen.main.Section
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.ScoreUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

class MainViewModel(
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    iExamRepository: IExamRepository,

    ) : ViewModel() {


    private var type: ExamType = ExamType.YEAR

    private val _mainState =
        MutableStateFlow(MainState(listOfAllExams = emptyList<ExamUiState>().toImmutableList()))
    val mainState = _mainState.asStateFlow()

    init {
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

            val allQuestions = emptyList<List<QuestionUiState>>().toMutableList()
            when (type) {
                ExamType.YEAR, ExamType.RANDOM -> {
                    val list = getAllQuestions(exam.id)
                    when (typeIndex) {
                        0 -> {

                            allQuestions.add(list.filter { it.isTheory.not() })
                            allQuestions.add(list.filter { it.isTheory })
                        }

                        1 -> {

                            allQuestions.add(list.filter { it.isTheory.not() })
                        }

                        else -> {
                            allQuestions.add(list.filter { it.isTheory })

                        }
                    }
                }

                ExamType.FAST_FINGER -> {
                    allQuestions.add(getAllQuestions(null).filter { it.isTheory.not() })
                }

            }

            val section = allQuestions
                .map { questionUiStates ->
                    val isTheory = questionUiStates.all { it.isTheory }
                    Section(stringRes = if (isTheory) 1 else 0, false)
                }



            Timber.e("time ${exam.examTime}")
            val time = when (type) {
                ExamType.RANDOM, ExamType.YEAR -> exam.examTime * 60L
                ExamType.FAST_FINGER -> allQuestions[0].size * 30L
            }

//            val objTime = (allQuestions.filter { it.isTheory.not() }.size) * time
//            val theoryTime = (allQuestions.filter { it.isTheory }.size) * 40L
//            Timber.e("tTime $theoryTime objTime $objTime")
            val choose = allQuestions.map {
                List(it.size) { -1 }
            }

            _mainState.update { state ->
                state.copy(
                    currentExam = exam,
                    questions = allQuestions.map { it.toImmutableList() }.toImmutableList(),
                    choose = choose.map { it.toImmutableList() }.toImmutableList(),
                    currentSectionIndex = 0,
                    sections = section.toImmutableList(),
                    totalTime = time, currentTime = 0, examPart = typeIndex,
                    isSubmit = false
                )
            }


        }
    }

    private suspend fun onContinueExam() {
        val currentExam1 = settingRepository.getCurrentExam()
        Timber.e("setting exam $currentExam1")

        if (currentExam1 != null) {

            val allQuestions = emptyList<List<QuestionUiState>>().toMutableList()


            val list = getAllQuestions(currentExam1.id)
            when (currentExam1.examPart) {
                0 -> {
                    allQuestions.add(list.filter { it.isTheory.not() })
                    allQuestions.add(list.filter { it.isTheory })

                }

                1 -> {

                    allQuestions.add(list.filter { it.isTheory.not() })
                }

                else -> {
                    allQuestions.add(list.filter { it.isTheory })

                }
            }


            val exam = mainState.value.listOfAllExams.find {
                currentExam1.id == it.id
            }
            val chooses = currentExam1.choose.map { it.toImmutableList() }.toImmutableList()
            val section = allQuestions
                .mapIndexed { index, questionUiStates ->
                    val isTheory = questionUiStates.all { it.isTheory }
                    val isFinished = chooses[index].all { it > -1 }
                    Section(stringRes = if (isTheory) 1 else 0, isFinished)
                }

            _mainState.update { state ->
                state.copy(
                    currentExam = exam,
                    examPart = currentExam1.examPart,
                    currentTime = currentExam1.currentTime,
                    totalTime = currentExam1.totalTime,
                    isSubmit = currentExam1.isSubmit,
                    currentSectionIndex = currentExam1.paperIndex,
                    choose = chooses,
                    sections = section.toImmutableList(),
                    questions = allQuestions.map { it.toImmutableList() }.toImmutableList()
                )
            }

            if (currentExam1.isSubmit) {
                markExam()
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

    fun onOption(sectionIndex: Int, questionIndex: Int, optionIndex: Int?) {

//
        val chooses = mainState.value.choose.toMutableList()
        val choose = chooses[sectionIndex].toMutableList()
        choose[questionIndex] = optionIndex ?: 2
        chooses[sectionIndex] = choose.toImmutableList()

        val isFinished = choose.all { it > -1 }
        val sections = mainState.value.sections.toMutableList()
        sections[sectionIndex] = sections[sectionIndex].copy(isFinished = isFinished)

        val currentSection = if (isFinished) {
            val newIndex = sectionIndex + 1
            if (sections.getOrNull(newIndex) == null) sectionIndex else newIndex
        } else {
            sectionIndex
        }

        _mainState.update {
            it.copy(
                choose = chooses.toImmutableList(),
                sections = sections.toImmutableList(),
                currentSectionIndex = currentSection
            )
        }

        //add and remove Choose
    }


    private suspend fun saveCurrentExam() {

        val id = mainState.value

        if (id.currentExam != null && type.save) {

            settingRepository.setCurrentExam(
                CurrentExam(
                    id = id.currentExam.id,
                    currentTime = id.currentTime,
                    totalTime = id.totalTime,
                    isSubmit = id.isSubmit,
                    examPart = id.examPart,
                    paperIndex = mainState.value.currentSectionIndex,
                    choose = mainState.value.choose
                )
            )

        }


    }


    private var saveJob: Job? = null
    fun onTimeChanged(time: Long) {
        _mainState.update {
            it.copy(currentTime = time)
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
                    isSubmit = true,
                    currentTime = 0
                )

            }
            markExam()
            saveCurrentExam()
        }
    }

    fun onRetry() {
        val question = emptyList<List<QuestionUiState>>().toMutableList()
        _mainState.update { state ->
            state.copy(
                questions = question.map { it.toImmutableList() }.toImmutableList()
            )
        }

        mainState.value.currentExam?.let { examUiState ->
            val index = mainState.value.listOfAllExams.indexOfFirst { it.id == examUiState.id }
            Timber.e("retry index is $index")

            startExam(type, index, mainState.value.examPart)
        }


    }

    private fun markExam() {
        val answerIndex = mainState.value
            .questions[0]
            .filter { it.isTheory.not() }
            .map { questionUiState ->
                questionUiState.options.indexOfFirst { it.isAnswer }
            }
        val choose = mainState.value.choose[0]
        val size = choose.size
        val correct = answerIndex
            .mapIndexed { index, i ->
                choose[index] == i
            }
            .count { it }
        val inCorrect = answerIndex.size - correct

        val complete = choose.count { it > -1 }
        val skipped = size - complete
        val compPercent = ((complete / size.toFloat()) * 100).toInt()
        val grade = when (((correct / size.toFloat()) * 100).toInt()) {
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
                    correct = correct
                ),
                currentSectionIndex = 0
            )
        }
    }

    private fun getTitle(examId: Long, no: Long, isTheory: Boolean): String {
        val exam = mainState.value.listOfAllExams.find { it.id == examId }

        return "Waec ${exam?.year} ${if (isTheory) "Theo" else "Obj"} Q$no"
    }

    fun changeIndex(index: Int) {
        _mainState.update {
            it.copy(currentSectionIndex = index)
        }
    }



}
