package com.mshabiola.database.dao.subjectdao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Subject
import commshdabioladatabase.tables.SubjectQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class SubjectDao(
    private val subjectQueries: SubjectQueries,
    private val coroutineDispatcher: CoroutineDispatcher,
) : ISubjectDao {
    override suspend fun insert(subject: Subject) {
        println("insert subject $subject")
        withContext(coroutineDispatcher) {
            if (subject.id == -1L) {
                subjectQueries.insert(subject.toEntity())
            } else {
                subjectQueries.update(subject.name, subject.id)
            }
        }
    }

    override fun getAll(): Flow<List<Subject>> {
        return subjectQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(subject: Subject) {
        withContext(coroutineDispatcher) {
            subjectQueries.update(
                subject.name,
                subject.id,
            )
        }
    }

    override fun getOne(id: Long): Flow<Subject> {
        return subjectQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            subjectQueries.deleteByID(id)
        }
    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            subjectQueries.deleteAll()
        }
    }
}
