package com.mshabiola.database.dao.questiondao

import com.mshdabiola.model.data.Question
import kotlinx.coroutines.flow.Flow

interface IQuestionDao {

    suspend fun insert(question: Question)
    suspend fun insertOrReplace(question: Question)

    fun getAll(): Flow<List<Question>>

    suspend fun update(question: Question)
    fun getOne(id: Long): Flow<Question>
    suspend fun delete(id: Long)
}