package com.mshdabiola.data.repository

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

internal class ExamRepository(
    private val iExamDao: IExamDao,
    private val iSubjectDao: ISubjectDao
) : IExamRepository {
    override val allExams: Flow<List<ExamWithSub>>
        get() = iExamDao
            .getAllWithSub()


    override suspend fun insertExam(exam: Exam) {
        iExamDao.insert(exam)
    }

    override suspend fun deleteExam(examId: Long) {
        iExamDao.delete(examId)
    }



    override suspend fun updateExam(exam: Exam) {
        iExamDao.update(exam)
    }

    override fun getExamBySubjectId(subId: Long): Flow<List<ExamWithSub>> {
        return iExamDao.getAllBySubjectIdWithSub(subId)
    }

}