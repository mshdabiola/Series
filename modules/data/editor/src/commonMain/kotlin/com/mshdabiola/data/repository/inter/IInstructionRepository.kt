package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.Flow

interface IInstructionRepository {

    suspend fun insert(instruction: Instruction)

    fun getAll(examId: Long): Flow<List<Instruction>>

    fun getOne(id: Long): Flow<Instruction>
    suspend fun delete(id: Long)
    suspend fun insertAll(instructionList: List<Instruction>)
    suspend fun deleteAll()
}