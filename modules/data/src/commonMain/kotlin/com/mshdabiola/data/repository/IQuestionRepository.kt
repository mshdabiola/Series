package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Question
import kotlinx.coroutines.flow.Flow

interface IQuestionRepository {

    suspend fun upsert(question: Question): Long
    suspend fun upsertMany(question: List<Question>)

    fun getAll(): Flow<List<Question>>

    fun getOne(id: Long): Flow<Question?>

    fun getByExamId(examId: Long): Flow<List<Question>>
    fun getByRandom(number: Long): Flow<List<Question>>

    suspend fun delete(id: Long)
    suspend fun deleteOption(id: Long)
}
