package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import com.mshdabiola.datastore.model.CurrentExamSer
import com.mshdabiola.datastore.model.InstructionSer
import com.mshdabiola.datastore.model.QuestionSer
import com.mshdabiola.datastore.model.UserDataSer
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

internal class StoreImpl(
    private val userdata: DataStore<UserDataSer>,
    private val question: DataStore<Map<Long, QuestionSer>>,
    private val instruction: DataStore<Map<Long, InstructionSer>>,
    private val current: DataStore<CurrentExamSer>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : Store {

    override val userData: Flow<UserDataSer>
        get() = userdata
            .data
            .flowOn(coroutineDispatcher)
    override val questions: Flow<Map<Long, QuestionSer>>
        get() = question
            .data
            .flowOn(coroutineDispatcher)
    override val instructions: Flow<Map<Long, InstructionSer>>
        get() = instruction
            .data
            .flowOn(coroutineDispatcher)
    override val currentExam: Flow<CurrentExamSer>
        get() = current
            .data
            .flowOn(coroutineDispatcher)

    override suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer {
        return withContext(coroutineDispatcher) {
            userdata.updateData(transform)
        }
    }

    override suspend fun updateQuestion(transform: suspend (Map<Long, QuestionSer>) -> Map<Long, QuestionSer>): Map<Long, QuestionSer> {
        return withContext(coroutineDispatcher) {
            question.updateData(transform)
        }
    }

    override suspend fun updateInstruction(transform: suspend (Map<Long, InstructionSer>) -> Map<Long, InstructionSer>): Map<Long, InstructionSer> {
        return withContext(coroutineDispatcher) {
            instruction.updateData(transform)
        }
    }


    override suspend fun updateCurrentQuestion(transform: suspend (CurrentExamSer) -> CurrentExamSer): CurrentExamSer {
        return withContext(coroutineDispatcher) {
            current.updateData(transform)
        }
    }


}
