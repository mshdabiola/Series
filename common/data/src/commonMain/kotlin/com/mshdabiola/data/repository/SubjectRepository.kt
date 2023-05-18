package com.mshdabiola.data.repository

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

internal class SubjectRepository(
    private val iExamDao: IExamDao,
    private val iSubjectDao: ISubjectDao
) : ISubjectRepository {
    override val subjectAndExams: Flow<List<ExamWithSub>>
        get() = iExamDao
            .getAllWithSub()
    override val subjects: Flow<List<Subject>>
        get() = iSubjectDao
            .getAll()

    override suspend fun insertSubject(subject: Subject) {
        iSubjectDao.insert(subject)
    }

    override suspend fun insertExam(exam: Exam) {
        iExamDao.insert(exam)
    }

    override suspend fun deleteExam(examId: Long) {
        iExamDao.delete(examId)
    }

    override suspend fun deleteSubject(subjectId: Long) {
        iSubjectDao.delete(subjectId)
    }

    override suspend fun updateExam(exam: Exam) {
        iExamDao.update(exam)
    }

    override suspend fun updateSubject(subject: Subject) {
        iSubjectDao.update(subject)
    }
}