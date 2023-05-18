package com.mshabiola.database.dao.exam

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import commshdabioladatabase.tables.ExamQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ExamDao(
    private val examQueries: ExamQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IExamDao {
    override suspend fun insert(exam: Exam) {
        withContext(coroutineDispatcher) {
            examQueries.insert(exam.toEntity())
        }
    }

    override suspend fun update(exam: Exam) {
        withContext(coroutineDispatcher) {
            examQueries.update(exam.subjectID, exam.year, exam.id)
        }
    }

    override fun getAll(): Flow<List<Exam>> {
        return examQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getAllWithSub(): Flow<List<ExamWithSub>> {
        return examQueries
            .getAllWithExam()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map {
                it.map {
                    ExamWithSub(it.id, it.subjectId, it.year, it.name)
                }
            }

    }

    override fun getBySubject(subjectID: Long): Flow<List<Exam>> {
        return examQueries
            .getBySubject(subjectID)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getOne(id: Long): Flow<Exam> {
        return examQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            examQueries.deleteByID(id)
        }
    }
}