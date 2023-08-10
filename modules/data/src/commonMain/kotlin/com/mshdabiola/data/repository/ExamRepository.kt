package com.mshdabiola.data.repository

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn

internal class ExamRepository(
    private val iExamDao: IExamDao,
    private val iSubjectDao: ISubjectDao
) : IExamRepository {


    override fun getAllWithSub(): Flow<List<ExamWithSub>> {
        return iExamDao
            .getAllWithSub()
    }

    override fun getOne(id:Long): Flow<Exam> {
        return iExamDao
            .getOne(id)
    }

    override suspend fun deleteAll() {
        iExamDao.deleteAll()
    }

    override suspend fun updateType(id: Long, isOnlyObj: Boolean) {
        iExamDao.updateType(id, isOnlyObj)
    }

    override suspend fun insertAll(exams: List<Exam>) {
        exams.forEach {
            insertExam(it)
        }
    }


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