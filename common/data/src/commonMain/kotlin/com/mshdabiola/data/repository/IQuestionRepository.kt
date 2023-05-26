package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionWithOptions
import kotlinx.coroutines.flow.Flow

interface IQuestionRepository {


    suspend fun insert(questionWithOptions: QuestionWithOptions)

    fun getAllWithExamId(examId: Long): Flow<List<QuestionWithOptions>>

    fun getAll(): Flow<List<Question>>

    suspend fun delete(id:Long)

    suspend fun insertMany(questionWithOptions: List<QuestionWithOptions>)
}