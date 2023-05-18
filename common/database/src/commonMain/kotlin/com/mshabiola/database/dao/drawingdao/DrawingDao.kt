package com.mshabiola.database.dao.drawingdao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Drawing
import commshdabioladatabase.tables.DrawingQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class DrawingDao(
    private val drawingQueries: DrawingQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IDrawingDao {
    override suspend fun insert(drawing: Drawing) {
        withContext(coroutineDispatcher) {
            drawingQueries.insert(drawing.toEntity())
        }
    }

    override fun getAll(): Flow<List<Drawing>> {
        return drawingQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(drawing: Drawing) {
        withContext(coroutineDispatcher) {
            drawingQueries.update(drawing.path, drawing.id)
        }

    }

    override fun getOne(id: Long): Flow<Drawing> {
        return drawingQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            drawingQueries.deleteByID(id)
        }
    }


}