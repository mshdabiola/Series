package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {


    val subjectAndExams: Flow<List<ExamWithSub>>
    val subjects: Flow<List<Subject>>

    suspend fun insertSubject(subject: Subject)

    suspend fun insertExam(exam: Exam)

    suspend fun deleteExam(examId: Long)
    suspend fun deleteSubject(subjectId: Long)

    suspend fun updateExam(exam: Exam)
    suspend fun updateSubject(subject: Subject)
}