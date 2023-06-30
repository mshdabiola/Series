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
            if (option.id == -1L)
                optionQueries.insert(option.toEntity())
            else
                optionQueries.insertOrReplace(option.toEntity())
        }
    }

    override suspend fun insertMany(options: List<Option>) {
        options.forEach {
            insert(it)
        }
    }

    override fun getAll(): Flow<List<Option>> {
        return optionQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getAllByExamId(examId: Long): Flow<List<Option>> {
        return optionQueries
            .getAllByExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override suspend fun update(option: Option) {
        withContext(coroutineDispatcher) {
            val optionEntity = option.toEntity()
            optionQueries.update(
                questionNos = optionEntity.questionNos,
                content = optionEntity.content,
                isAnswer = optionEntity.isAnswer,
                id = optionEntity.id,
                nos = optionEntity.nos
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

    override suspend fun deleteQuestionNos(questionNos: Long, examId: Long) {
        withContext(coroutineDispatcher) {
            optionQueries.deleteByQuestionNos(questionNos, examId)
        }
    }

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher){
            optionQueries.deleteAll()
        }
    }

}