package com.mshabiola.database.dao.instructiondao

import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.Flow

interface IInstructionDao {

    suspend fun insert(instruction: Instruction)

    fun getAll(examId: Long): Flow<List<Instruction>>

    suspend fun update(instruction: Instruction)
    fun getOne(id: Long): Flow<Instruction>
    suspend fun delete(id: Long)
}