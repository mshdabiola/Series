package com.mshdabiola.data.repository

import com.mshdabiola.data.model.asEntity
import com.mshdabiola.data.model.asModel
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.model.data.Question
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class QuestionRepository constructor(
    private val questionDao: QuestionDao,
    private val optionDao: OptionDao,
    private val ioDispatcher: CoroutineDispatcher,
) : IQuestionRepository {
    override suspend fun upsert(question: Question): Long {
        return withContext(ioDispatcher) {

            val id = questionDao.upsert(question.asModel())

            question
                .options
                ?.map { it.asEntity().copy(questionId = id) }
                ?.let {
                    optionDao.insertAll(it)
                }

            id
        }
    }

    override suspend fun upsertMany(question: List<Question>) {
        question.forEach {
            upsert(it)
        }
    }

    override fun getAll(): Flow<List<Question>> {
        return questionDao
            .getAll()
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getOne(id: Long): Flow<Question?> {
        return questionDao
            .getOne(id)
            .map { it?.asModel() }
            .flowOn(ioDispatcher)
    }

    override fun getByExamId(examId: Long): Flow<List<Question>> {
        return questionDao
            .getByExamId(examId)
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override fun getByRandom(number: Long): Flow<List<Question>> {
        return questionDao
            .getByRandom(number)
            .map { noteEntities ->
                noteEntities.map {
                    it.asModel()
                }
            }
            .flowOn(ioDispatcher)
    }

    override suspend fun delete(id: Long) {
        withContext(ioDispatcher) {
            questionDao.delete(id)
        }
    }

    override suspend fun deleteOption(id: Long) {
        optionDao.delete(id)
    }

}
