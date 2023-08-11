package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow

interface IQuestionRepository {


    suspend fun insert(questionFull: QuestionFull)

    suspend fun insertAll(questionFull: List<QuestionFull>)

    fun getAllWithExamId(examId: Long): Flow<List<QuestionFull>>
    fun getRandom(num: Long): Flow<List<QuestionFull>>

    fun getAll(): Flow<List<Question>>

    suspend fun delete(id: Long)

    suspend fun insertMany(questionWithOptions: List<QuestionFull>)
    suspend fun deleteAll()

    suspend fun deleteOption(id: Long)


}