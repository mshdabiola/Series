package com.mshabiola.database.dao.instructiondao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Instruction
import commshdabioladatabase.tables.InstructionQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class InstructionDao(
    private val instructionQueries: InstructionQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IInstructionDao {
    override suspend fun insert(instruction: Instruction) {
        withContext(coroutineDispatcher) {
            if (instruction.id == -1L)
                instructionQueries.insert(instruction.toEntity())
            else {
                val entity = instruction.toEntity()
                instructionQueries.update(
                    title = entity.title,
                    content = entity.content,
                    examId = entity.examId,
                    id = entity.id
                )
            }

        }
    }

    override fun getAll(examId: Long): Flow<List<Instruction>> {
        return instructionQueries
            .getAllByExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(instruction: Instruction) {
        withContext(coroutineDispatcher) {
            instructionQueries.update(

                instruction.title,
                instruction.content,
                instruction.examId,
                instruction.id
            )
        }
    }

    override fun getOne(id: Long): Flow<Instruction> {
        return instructionQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            instructionQueries.deleteByID(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            instructionQueries.deleteAll()
        }
    }
}