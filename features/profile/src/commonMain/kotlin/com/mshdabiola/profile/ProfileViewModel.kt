/*
 *abiola 2022
 */

package com.mshdabiola.profile

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

class ProfileViewModel constructor(

    ) : ViewModel(){

}