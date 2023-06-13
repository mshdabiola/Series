package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.StateFlow

interface ISettingRepository {

    val dummy: StateFlow<DummySetting>
    suspend fun setDummy(dummy: DummySetting)
    suspend fun setCurrentInstruction(instruction: Instruction)

    fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)


    suspend fun setCurrentQuestion(question: QuestionFull)

    fun getCurrentQuestion(examId: Long): QuestionFull?

    suspend fun removeQuestion(examId: Long)
}