package com.mshabiola.database.dao.subjectdao

import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectDao {

    suspend fun insert(subject: Subject)

    fun getAll(): Flow<List<Subject>>

    suspend fun update(subject: Subject)
    fun getOne(id: Long): Flow<Subject>
    suspend fun delete(id: Long)
}