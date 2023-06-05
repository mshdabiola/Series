package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.StateFlow

interface ISettingRepository {

    val dummy: StateFlow<DummySetting>
    suspend fun setDummy(dummy: DummySetting)
    suspend fun setCurrentInstruction(instruction: Instruction)

    fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)
}