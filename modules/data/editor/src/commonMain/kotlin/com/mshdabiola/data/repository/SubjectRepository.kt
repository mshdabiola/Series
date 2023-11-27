package com.mshdabiola.data.repository

import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

internal class SubjectRepository(
    private val iSubjectDao: ISubjectDao,
) : ISubjectRepository {


    override fun getAll(): Flow<List<Subject>> {
        return iSubjectDao
            .getAll()
    }

    override suspend fun insertAll(subjects: List<Subject>) {
        subjects.forEach { insertSubject(it) }
    }

    override suspend fun insertSubject(subject: Subject) {
        iSubjectDao.insert(subject)
    }


    override suspend fun deleteSubject(subjectId: Long) {
        iSubjectDao.delete(subjectId)
    }


    override suspend fun updateSubject(subject: Subject) {
        iSubjectDao.update(subject)
    }

    override suspend fun deleteAll() {
        iSubjectDao.deleteAll()
    }
}