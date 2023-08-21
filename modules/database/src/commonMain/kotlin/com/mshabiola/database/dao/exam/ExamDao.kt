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
            if (exam.id == -1L)
                examQueries.insert(exam.toEntity())
            else {
                val entity = exam.toEntity()
                examQueries.update(
                    subjectId = entity.subjectId,
                    year = entity.year,
                    examTime = entity.examTime,
                    id = entity.id
                )

            }

        }
    }

    override suspend fun update(exam: Exam) {
        withContext(coroutineDispatcher) {
            examQueries.update(
                subjectId = exam.subjectID,
                year = exam.year,
                examTime = exam.examTime,
                id = exam.id
            )
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
            .getAllWithSubject()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { getAllWithSubjects ->
                getAllWithSubjects.map {
                    ExamWithSub(
                        id = it.id,
                        subjectID = it.subjectId,
                        year = it.year,
                        isObjOnly = it.isObjOnly == 0L,
                        subject = it.name,
                        examTime = it.examTime
                    )
                }
            }

    }

    override fun getAllBySubjectIdWithSub(subjectID: Long): Flow<List<ExamWithSub>> {
        return examQueries
            .getAllBySubjectIdWithSubject(subjectID)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { getAllBySubjectIdWithSubjects ->
                getAllBySubjectIdWithSubjects.map {
                    ExamWithSub(
                        id = it.id,
                        subjectID = it.subjectId,
                        year = it.year,
                        isObjOnly = it.isObjOnly == 0L,
                        subject = it.name,
                        examTime = it.examTime
                    )
                }
            }

    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            examQueries.deleteAll()
        }

    }

    override suspend fun updateType(id: Long, isOnlyObj: Boolean) {
        examQueries.updateType(if (isOnlyObj) 0 else 1, id)
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