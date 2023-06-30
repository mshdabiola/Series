package com.mshabiola.database.dao.exam

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import kotlinx.coroutines.flow.Flow

interface IExamDao {

    suspend fun insert(exam: Exam)
    suspend fun update(exam: Exam)

    fun getAll(): Flow<List<Exam>>

    fun getAllWithSub(): Flow<List<ExamWithSub>>

    fun getBySubject(subjectID: Long): Flow<List<Exam>>

    fun getOne(id: Long): Flow<Exam>
    suspend fun delete(id: Long)

    fun getAllBySubjectIdWithSub(subjectID: Long): Flow<List<ExamWithSub>>
    suspend fun deleteAll()
}