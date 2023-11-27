package com.mshabiola.database.dao.topicdao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Topic
import commshdabioladatabase.tables.TopicQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class TopicDao(
    private val topicQueries: TopicQueries,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ITopicDao {
    override suspend fun insert(topic: Topic) {
        withContext(coroutineDispatcher) {
            if (topic.id == -1L)
                topicQueries.insert(topic.toEntity())
            else {
                val entity = topic.toEntity()
                topicQueries.update(
                    name = entity.name,
                    subjectId = entity.subjectId,
                    id = entity.id
                )
            }
        }
    }

    override fun getAll(): Flow<List<Topic>> {
        return topicQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getAllBySubject(subjectId: Long): Flow<List<Topic>> {
        return topicQueries
            .getAllBySubject(subjectId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(topic: Topic) {
        withContext(coroutineDispatcher) {
            topicQueries.update(
                topic.name,
                topic.subjectId,
                topic.id,

                )
        }
    }

    override fun getOne(id: Long): Flow<Topic> {
        return topicQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            topicQueries.deleteByID(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            topicQueries.deleteAll()
        }
    }
}