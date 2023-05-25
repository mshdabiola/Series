package com.mshabiola.database.dao.questiondao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionWithOptions
import com.mshdabiola.model.data.toQuestionWithOptions
import commshdabioladatabase.tables.OptionQueries
import commshdabioladatabase.tables.QuestionQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class QuestionDao(
    private val questionQueries: QuestionQueries,
    private val optionQueries: OptionQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IQuestionDao {
    override suspend fun insert(question: Question) {
        withContext(coroutineDispatcher) {
            println("insert $question")
            questionQueries.insert(question.toEntity())
        }
    }

    override suspend fun insertOrReplace(question: Question) {
        withContext(coroutineDispatcher) {
            questionQueries.insertReplace(question.toEntity())
        }
    }

    override fun getAll(): Flow<List<Question>> {
        return questionQueries
            .getAll()
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getAllWithOptions(examId: Long): Flow<List<QuestionWithOptions>> {
        return questionQueries
            .getAllWithExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
            .map { questionList ->
                questionList.map {
                    val list = optionQueries
                        .getAllWithQuestionNo(it.nos)
                        .executeAsList()
                        .map { it.toModel() }
                    it.toQuestionWithOptions(list)
                }
            }
            .flowOn(coroutineDispatcher)
    }

    override suspend fun update(question: Question) {
        withContext(coroutineDispatcher) {
            val questione = question.toEntity()
            questionQueries.update(
                questione.examId,
                questione.content,
                questione.isTheory,
                questione.answer,
                questione.instructionId,
                questione.topicId,
                questione.id
            )
        }
    }

    override fun getOne(id: Long): Flow<Question> {
        return questionQueries
            .getById(id)
            .asFlow()
            .mapToOne(coroutineDispatcher)
            .map { it.toModel() }
    }

    override suspend fun delete(id: Long) {
        withContext(coroutineDispatcher) {
            questionQueries.deleteByID(id)
        }
    }
}