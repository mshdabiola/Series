package com.mshdabiola.data.repository

import com.mshabiola.database.dao.topicdao.ITopicDao
import com.mshdabiola.data.repository.inter.ITopicRepository
import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.flow.Flow

internal class TopicRepository(
    private val iTopicDao: ITopicDao,
) : ITopicRepository {
    override suspend fun insert(topic: Topic) {
        iTopicDao.insert(topic)
    }

    override fun getAll(): Flow<List<Topic>> {
        return iTopicDao
            .getAll()
    }

    override fun getAllBySubject(subjectId: Long): Flow<List<Topic>> {
        return iTopicDao
            .getAllBySubject(subjectId)
    }

    override suspend fun update(topic: Topic) {
        iTopicDao.update(topic)
    }

    override suspend fun insertAll(topics: List<Topic>) {
        topics.forEach {
            insert(it)
        }
    }

    override suspend fun deleteAll() {
        iTopicDao.deleteAll()
    }

    override fun getOne(id: Long): Flow<Topic> {
        return iTopicDao.getOne(id)
    }

    override suspend fun delete(id: Long) {
        iTopicDao.delete(id)
    }
}