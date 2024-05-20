/*
 *abiola 2022
 */

package com.mshdabiola.mainc

import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel(
    private val iExamRepository: IExaminationRepository,
    private val settingRepository: ISettingRepository

) : ViewModel() {


    private val _mainState =
        MutableStateFlow(MainState(listOfAllExams = emptyList<ExamUiState>().toImmutableList()))
    val mainState = _mainState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            iExamRepository
                .getAll()
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

        viewModelScope.launch {
            settingRepository.currentExam
                .collectLatest { curret ->

                    val exam = iExamRepository.getOne(curret.id).firstOrNull()

                    val chooses = curret.choose.map { it.toImmutableList() }.toImmutableList()
                    _mainState.update {
                        it.copy(
                            examPart = curret.examPart,
                            currentTime = curret.currentTime,
                            totalTime = curret.totalTime,
                            isSubmit = curret.isSubmit,
                            choose = chooses,
                            currentExam = exam?.toUi(),
                        )
                    }
                }
        }
    }

}