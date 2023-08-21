package com.mshdabiola.setting

import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MultiplatformSettings {

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?
}
