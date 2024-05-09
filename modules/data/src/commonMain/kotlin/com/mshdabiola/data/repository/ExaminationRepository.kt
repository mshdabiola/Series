package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asExam
import com.mshdabiola.data.model.asExamEntity
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.model.data.Examination
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class ExaminationRepository constructor(
    private val examinationDao: ExaminationDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IExaminationRepository {


    override suspend fun upsert(examination: Examination): Long {
        return withContext(ioDispatcher) {
            examinationDao.upsert(examination.asExamEntity())
        }
    }

    override fun getAll(): Flow<List<Examination>> {
        return examinationDao
            .getAll()
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getAllBuSubjectId(subjectId: Long): Flow<List<Examination>> {
        return examinationDao
            .getAllBySubjectId(subjectId)
            .map { fullList ->
                fullList.map { it.asExam() }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Examination?> {
        return examinationDao
            .getOne(id)
            .map { it?.asExam() }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            examinationDao.delete(id)
        }
    }

    override suspend fun export(
        examsId: List<Long>,
        path: String,
        name: String,
        version: Int,
        key: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun import(path: String, key: String) {
        TODO("Not yet implemented")
    }

}
