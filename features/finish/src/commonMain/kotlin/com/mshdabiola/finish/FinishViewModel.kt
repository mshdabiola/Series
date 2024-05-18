/*
 *abiola 2022
 */

package com.mshdabiola.finish

import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ISubjectRepository
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.ExamType
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.MainState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.ScoreUiState
import com.mshdabiola.ui.state.Section
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FinishViewModel constructor(
    private val iSubjectRepository: ISubjectRepository,
    private val settingRepository: ISettingRepository,
    private val questionRepository: IQuestionRepository,
    private val iExamRepository: IExaminationRepository,

    ) : ViewModel(){

    private var type: ExamType = ExamType.YEAR

    private val _mainState =
        MutableStateFlow(MainState(listOfAllExams = emptyList<ExamUiState>().toImmutableList()))
    val mainState = _mainState.asStateFlow()

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