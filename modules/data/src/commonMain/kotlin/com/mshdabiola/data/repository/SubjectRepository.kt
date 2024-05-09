package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asEntity
import com.mshdabiola.data.model.asSub
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class SubjectRepository constructor(
    private val subjectDao: SubjectDao,
    private val ioDispatcher: CoroutineDispatcher,
) : ISubjectRepository {
    override suspend fun upsert(subject: Subject): Long {
        return withContext(ioDispatcher) {

            subjectDao.upsert(subject.asEntity())

        }
    }

    override fun getAll(): Flow<List<Subject>> {
        return subjectDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asSub()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Subject?> {
        return subjectDao
            .getOne(id)
            .map { it?.asSub() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            subjectDao.delete(id)
        }
    }

}
