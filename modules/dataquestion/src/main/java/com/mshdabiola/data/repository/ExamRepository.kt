package com.mshdabiola.data.repository

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import kotlinx.coroutines.flow.Flow

internal class ExamRepository(
    private val iExamDao: IExamDao,
) : IExamRepository {


    override fun getAllWithSub(): Flow<List<ExamWithSub>> {
        return iExamDao
            .getAllWithSub()
    }
}