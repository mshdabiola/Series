package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Examination
import kotlinx.coroutines.flow.Flow

interface IExaminationRepository {

    suspend fun upsert(examination: Examination): Long
    fun getAll(): Flow<List<Examination>>

    fun getAllBuSubjectId(subjectId: Long): Flow<List<Examination>>

    fun getOne(id: Long): Flow<Examination?>

    suspend fun delete(id: Long)

    suspend fun export(
        examsId: List<Long>,
        path: String,
        name: String,
        version: Int,
        key: String,
    )

    suspend fun import(
        path: String,
        key: String,
    )
}
