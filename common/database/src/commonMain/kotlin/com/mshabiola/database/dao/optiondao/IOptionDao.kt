package com.mshabiola.database.dao.optiondao

import com.mshdabiola.model.data.Option
import kotlinx.coroutines.flow.Flow

interface IOptionDao {

    suspend fun insert(option: Option)
    suspend fun insertOrReplace(option: Option)


    fun getAll(): Flow<List<Option>>

    suspend fun update(option: Option)
    fun getOne(id: Long): Flow<Option>
    suspend fun delete(id: Long)
}