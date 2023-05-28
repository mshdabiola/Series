package com.mshdabiola.data.repository

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

internal class SubjectRepository(
    private val iSubjectDao: ISubjectDao
) : ISubjectRepository {

    override val subjects: Flow<List<Subject>>
        get() = iSubjectDao
            .getAll()

    override suspend fun insertSubject(subject: Subject) {
        iSubjectDao.insert(subject)
    }


    override suspend fun deleteSubject(subjectId: Long) {
        iSubjectDao.delete(subjectId)
    }


    override suspend fun updateSubject(subject: Subject) {
        iSubjectDao.update(subject)
    }
}