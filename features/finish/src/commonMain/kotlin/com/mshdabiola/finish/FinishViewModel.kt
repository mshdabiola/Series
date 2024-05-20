/*
 *abiola 2022
 */

package com.mshdabiola.finish

import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.ExamType
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.ScoreUiState
import com.mshdabiola.ui.state.Section
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FinishViewModel constructor(
    private val iSubjectRepository: ISubjectRepository,
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    private val iExamRepository: IExaminationRepository,

    ) : ViewModel() {


    private val _mainState =
        MutableStateFlow(MainState())
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch {
            var current = settingRepository.currentExam.first()

            current = current.copy(isSubmit = true)

            settingRepository.setCurrentExam(current)

            val exam = iExamRepository.getOne(current.id).first()!!
            val questions = questionRepository.getByExamId(current.id).first()
                .map { it.toQuestionUiState() }

            val allQuestions = emptyList<List<QuestionUiState>>().toMutableList()

            allQuestions.add(questions.filter { it.isTheory.not() })
            allQuestions.add(questions.filter { it.isTheory })


            val section = allQuestions
                .map { questionUiStates ->
                    val isTheory = questionUiStates.all { it.isTheory }
                    Section(stringRes = if (isTheory) 1 else 0, false)
                }

            val choose=current.choose.map { it.toImmutableList() }.toImmutableList()


            _mainState.update {
                it.copy(
                    examination = exam.toUi(),
                    questions = allQuestions.map { it.toImmutableList() }.toImmutableList(),
                    sections = section.toImmutableList(),
                    choose = choose,
                    typeIndex = current.examPart
                )
            }

            markExam()

        }
    }

    private fun markExam() {
        val answerIndex = mainState.value
            .questions[0]
            .filter { it.isTheory.not() }
            .map { questionUiState ->
                questionUiState.options!!.indexOfFirst { it.isAnswer }
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
                    correct = correct,
                ),
                currentSectionIndex = 0,
            )
        }
    }


}