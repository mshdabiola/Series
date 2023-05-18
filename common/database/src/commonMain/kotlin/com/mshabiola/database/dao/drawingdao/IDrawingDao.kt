package com.mshabiola.database.dao.drawingdao

import com.mshdabiola.model.data.Drawing
import kotlinx.coroutines.flow.Flow

interface IDrawingDao {

    suspend fun insert(drawing: Drawing)

    fun getAll(): Flow<List<Drawing>>

    suspend fun update(drawing: Drawing)
    fun getOne(id: Long): Flow<Drawing>
    suspend fun delete(id: Long)
}