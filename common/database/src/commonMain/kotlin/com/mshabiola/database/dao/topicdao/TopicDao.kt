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
    private val coroutineDispatcher: CoroutineDispatcher
) : ITopicDao {
    override suspend fun insert(topic: Topic) {
        withContext(coroutineDispatcher) {
            topicQueries.insert(topic.toEntity())
        }
    }

    override fun getAll(): Flow<List<Topic>> {
        return topicQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(topic: Topic) {
        withContext(coroutineDispatcher) {
            topicQueries.update(
                topic.name,
                topic.id
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
}