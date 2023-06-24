package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import kotlinx.coroutines.flow.Flow

interface IExamRepository {

    suspend fun insertAll(exams: List<Exam>)


    suspend fun insertExam(exam: Exam)

    suspend fun deleteExam(examId: Long)

    suspend fun updateExam(exam: Exam)

    fun getExamBySubjectId(subId: Long): Flow<List<ExamWithSub>>
    fun getAllWithSub(): Flow<List<ExamWithSub>>
}