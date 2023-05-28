package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {


    val subjects: Flow<List<Subject>>

    suspend fun insertSubject(subject: Subject)

    suspend fun deleteSubject(subjectId: Long)

    suspend fun updateSubject(subject: Subject)
}