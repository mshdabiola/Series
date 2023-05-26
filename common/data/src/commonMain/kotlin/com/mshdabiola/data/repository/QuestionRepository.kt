package com.mshdabiola.data.repository

import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionWithOptions
import com.mshdabiola.model.data.toQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

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

    override suspend fun delete(id: Long) {
        val question=iQuestionDao.getOne(id).firstOrNull()
        question?.let {
            iOptionDao.deleteQuestionNos(it.nos,it.examId)
            iQuestionDao.delete(id)
        }

    }

    override suspend fun insertMany(questionWithOptions: List<QuestionWithOptions>) {
       questionWithOptions.forEach {
           insert(it)
       }
    }
}