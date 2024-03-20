package com.mshdabiola.data.repository

import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.setting.MultiplatformSettings

internal class SettingRepository(private val settings: MultiplatformSettings) : ISettingRepository {

    override suspend fun setCurrentInstruction(instruction: Instruction) {
        settings.setCurrentInstruction(instruction)
    }

    override suspend fun getCurrentInstruction(examId: Long): Instruction? {
        return settings.getCurrentInstruction(examId)
    }

    override suspend fun removeInstruction(examId: Long) {
        settings.removeInstruction(examId)
    }

    override suspend fun setCurrentQuestion(question: QuestionFull) {
        settings.setCurrentQuestion(question)
    }

    override suspend fun getCurrentQuestion(examId: Long): QuestionFull? {
        return settings.getCurrentQuestion(examId)
    }

    override suspend fun removeQuestion(examId: Long) {
        settings.removeQuestion(examId)
    }

    override suspend fun setCurrentExam(currentExam: CurrentExam?) {
        settings.setCurrentExam(currentExam)
    }

    override suspend fun getCurrentExam(): CurrentExam? {
        return settings.getCurrentExam()
    }
}
