package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.StateFlow

interface ISettingRepository {

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?
}