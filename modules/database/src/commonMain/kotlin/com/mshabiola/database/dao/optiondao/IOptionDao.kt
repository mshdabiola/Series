package com.mshabiola.database.dao.optiondao

import com.mshdabiola.model.data.Option
import kotlinx.coroutines.flow.Flow

interface IOptionDao {

    suspend fun insert(option: Option)
    suspend fun insertMany(options: List<Option>)
    fun getAll(): Flow<List<Option>>

    fun getAllByExamId(examId: Long): Flow<List<Option>>

    suspend fun update(option: Option)
    fun getOne(id: Long): Flow<Option>
    suspend fun delete(id: Long)
    suspend fun deleteQuestionNos(questionNos: Long, examId: Long)
}