package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.Flow

interface IInstructionRepository {

    suspend fun upsert(instruction: Instruction): Long

    suspend fun insertAll(instruction: List<Instruction>)

    fun getAll(): Flow<List<Instruction>>

    fun getAllByExamId(examId: Long): Flow<List<Instruction>>


    fun getOne(id: Long): Flow<Instruction?>

    suspend fun delete(id: Long)
}
