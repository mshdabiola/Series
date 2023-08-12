package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {


    fun getAll(): Flow<List<Subject>>

    suspend fun insertAll(subjects: List<Subject>)
    suspend fun insertSubject(subject: Subject)

    suspend fun deleteSubject(subjectId: Long)

    suspend fun updateSubject(subject: Subject)

    suspend fun deleteAll()
}