package com.mshabiola.database.dao.optiondao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Option
import commshdabioladatabase.tables.OptionQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class OptionDao(
    private val optionQueries: OptionQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IOptionDao {
    override suspend fun insert(option: Option) {
        withContext(coroutineDispatcher) {
            optionQueries.insert(option.toEntity())
        }
    }

    override fun getAll(): Flow<List<Option>> {
        return optionQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(optione: Option) {
        withContext(coroutineDispatcher) {
            val option = optione.toEntity()
            optionQueries.update(
                questionId = option.questionId,
                drawingId = option.drawingId,
                content = option.content,
                isAnswer = option.isAnswer,
                id = option.id
            )
        }
    }

    override fun getOne(id: Long): Flow<Option> {
        return optionQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            optionQueries.deleteByID(id)
        }
    }

}