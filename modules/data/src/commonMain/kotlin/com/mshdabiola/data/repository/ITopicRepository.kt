package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface ITopicRepository {

    suspend fun upsert(topic: Topic): Long
    suspend fun insertAll(topic: List<Topic>)

    fun getAll(): Flow<List<Topic>>
    fun getAllBySubject(subjectID: Long): Flow<List<Topic>>
    fun getOne(id: Long): Flow<Topic?>

    suspend fun delete(id: Long)

}
