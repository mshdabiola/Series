package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull

interface ISettingRepository {

    suspend fun setCurrentInstruction(instruction: Instruction)

    suspend fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)


    suspend fun setCurrentQuestion(question: QuestionFull)

    suspend fun getCurrentQuestion(examId: Long): QuestionFull?

    suspend fun removeQuestion(examId: Long)

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?
}