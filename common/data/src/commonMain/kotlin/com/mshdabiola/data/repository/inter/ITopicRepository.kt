package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface ITopicRepository {

    suspend fun insert(topic: Topic)

    fun getAll(): Flow<List<Topic>>
    fun getAllBySubject(subjectId: Long): Flow<List<Topic>>

    fun getOne(id: Long): Flow<Topic>
    suspend fun delete(id: Long)

    suspend fun update(topic: Topic)
}