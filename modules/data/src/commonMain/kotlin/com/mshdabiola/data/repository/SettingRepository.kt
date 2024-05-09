package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asModel
import com.mshdabiola.data.model.asSer
import com.mshdabiola.data.model.toSer
import com.mshdabiola.datastore.Store
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Question
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class SettingRepository(
    private val settings: Store,

    ) : ISettingRepository {
    override val instructions: Flow<Map<Long, Instruction>>
        get() = settings
            .instructions
            .map { instructionSers ->
                instructionSers.mapValues { it.value.asModel() }
            }
    override val questions: Flow<Map<Long, Question>>
        get() = settings
            .questions
            .map { instructionSers ->
                instructionSers.mapValues { it.value.asModel() }
            }
    override val currentExam: Flow<CurrentExam>
        get() = settings
            .currentExam
            .map { it.asModel() }

    override suspend fun setCurrentInstruction(instruction: Map<Long, Instruction>) {
        settings.updateInstruction { instruction.mapValues { it.value.toSer() } }
    }


    override suspend fun setCurrentQuestion(question: Map<Long, Question>) {
        settings.updateQuestion { question.mapValues { it.value.asSer() } }
    }


    override suspend fun setCurrentExam(currentExam: CurrentExam) {
        settings.updateCurrentQuestion { currentExam.toSer() }
    }


}
