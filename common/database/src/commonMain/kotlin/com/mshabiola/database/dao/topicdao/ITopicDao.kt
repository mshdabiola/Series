package com.mshabiola.database.dao.topicdao

import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.flow.Flow

interface ITopicDao {

    suspend fun insert(topic: Topic)

    fun getAll(): Flow<List<Topic>>

    suspend fun update(topic: Topic)
    fun getOne(id: Long): Flow<Topic>
    suspend fun delete(id: Long)
}