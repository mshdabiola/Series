package com.mshdabiola.setting

import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface MultiplatformSettings {

    val name: Flow<String>
    val dummy: StateFlow<DummySetting>

    suspend fun setName(name: String)

    suspend fun setDummy(dummy: DummySetting)

    suspend fun setCurrentInstruction(instruction: Instruction)

    fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)
}
