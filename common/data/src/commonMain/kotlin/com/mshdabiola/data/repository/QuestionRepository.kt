package com.mshdabiola.data.repository

import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionWithOptions
import com.mshdabiola.model.data.toQuestion
import kotlinx.coroutines.flow.Flow

internal class QuestionRepository(
    private val iQuestionDao: IQuestionDao,
    private val iOptionDao: IOptionDao
) : IQuestionRepository {

    override suspend fun insert(questionWithOptions: QuestionWithOptions) {
        println("reposi insert")

        questionWithOptions.options.forEach {
            iOptionDao.insert(it)
        }

        iQuestionDao.insert(questionWithOptions.toQuestion())
    }

    override fun getAllWithExamId(examId: Long): Flow<List<QuestionWithOptions>> {
        return iQuestionDao.getAllWithOptions(examId)
    }

    override fun getAll(): Flow<List<Question>> {
        return iQuestionDao.getAll()
    }
}