package com.mshabiola.database.dao.questiondao

import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow

interface IQuestionDao {

    suspend fun insert(question: Question)

    fun getAll(): Flow<List<Question>>

    fun getWithExamId(examId: Long): Flow<List<Question>>

    fun getAllWithOptions(examId: Long): Flow<List<QuestionFull>>

    fun getRandom(num: Long): Flow<List<QuestionFull>>

    suspend fun update(question: Question)
    fun getOne(id: Long): Flow<Question>
    suspend fun delete(id: Long)
    suspend fun deleteAll()

    suspend fun getMaxId():Long?
}