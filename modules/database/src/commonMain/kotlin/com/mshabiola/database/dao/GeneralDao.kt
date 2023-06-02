package com.mshabiola.database.dao

import kotlinx.coroutines.flow.Flow

interface GeneralDao<T> {

    suspend fun insert(t: T)

    fun getAll(): Flow<List<T>>

    suspend fun update(t: T)
    fun getOne(id: Long): Flow<T>
    suspend fun delete(id: Long)
}