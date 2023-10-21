package com.mshdabiola.setting

import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MultiplatformSettings {

    val name: Flow<String>

    suspend fun setName(name: String)


    suspend fun setCurrentInstruction(instruction: Instruction)

    suspend fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)

    suspend fun setCurrentQuestion(question: QuestionFull)

    suspend fun getCurrentQuestion(examId: Long): QuestionFull?

    suspend fun removeQuestion(examId: Long)

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?
}
