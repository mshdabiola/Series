package com.mshdabiola.data.repository

import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.model.CurrentExam
import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.setting.MultiplatformSettings
import kotlinx.coroutines.flow.StateFlow

internal class SettingRepository(private val settings: MultiplatformSettings) : ISettingRepository {
    override val dummy: StateFlow<DummySetting>
        get() = settings.dummy


    override suspend fun setDummy(dummy: DummySetting) {
        settings.setDummy(dummy)
    }

    override suspend fun setCurrentInstruction(instruction: Instruction) {
        settings.setCurrentInstruction(instruction)
    }

    override fun getCurrentInstruction(examId: Long): Instruction? {
        return settings.getCurrentInstruction(examId)
    }

    override suspend fun removeInstruction(examId: Long) {
        settings.removeInstruction(examId)
    }

    override suspend fun setCurrentQuestion(question: QuestionFull) {
        settings.setCurrentQuestion(question)
    }

    override fun getCurrentQuestion(examId: Long): QuestionFull? {
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