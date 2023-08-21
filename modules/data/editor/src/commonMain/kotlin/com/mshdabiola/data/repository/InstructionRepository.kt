package com.mshdabiola.data.repository

import com.mshabiola.database.dao.instructiondao.IInstructionDao
import com.mshdabiola.data.repository.inter.IInstructionRepository
import com.mshdabiola.model.data.Instruction
import kotlinx.coroutines.flow.Flow

internal class InstructionRepository(
    private val instructionDao: IInstructionDao
) : IInstructionRepository {
    override suspend fun insert(instruction: Instruction) {
        instructionDao.insert(instruction)
    }

    override fun getAll(examId: Long): Flow<List<Instruction>> {
        return instructionDao
            .getAll(examId)
    }

    override fun getOne(id: Long): Flow<Instruction> {
        return instructionDao
            .getOne(id)
    }

    override suspend fun delete(id: Long) {
        instructionDao.delete(id)
    }

    override suspend fun insertAll(instructionList: List<Instruction>) {
        instructionList.forEach {
            insert(it)
        }
    }

    override suspend fun deleteAll() {
        instructionDao.deleteAll()
    }
}