package com.mshdabiola.data.repository

import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.toQuestion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull

internal class QuestionRepository(
    private val iQuestionDao: IQuestionDao,
    private val iOptionDao: IOptionDao
) : IQuestionRepository {

    override suspend fun insert(questionFull: QuestionFull) {
        println("reposi insert")

        questionFull.options.forEach {
            iOptionDao.insert(it)
        }

        iQuestionDao.insert(questionFull.toQuestion())
    }

    override suspend fun insertAll(questionFull: List<QuestionFull>) {
        questionFull.forEach {
            insert(it)
        }
    }

    override fun getAllWithExamId(examId: Long): Flow<List<QuestionFull>> {
        return iQuestionDao.getAllWithOptions(examId)
    }

    override fun getRandom(num: Long): Flow<List<QuestionFull>> {
        return iQuestionDao.getRandom(num)
    }

    override fun getAll(): Flow<List<Question>> {
        return iQuestionDao.getAll()
    }

    override suspend fun delete(id: Long) {
        val question = iQuestionDao.getOne(id).firstOrNull()
        question?.let {
            iOptionDao.deleteQuestionNos(it.nos, it.examId)
            iQuestionDao.delete(id)
        }

    }

    override suspend fun insertMany(questionWithOptions: List<QuestionFull>) {
        questionWithOptions.forEach {
            insert(it)
        }
    }

    override suspend fun deleteAll() {
        iOptionDao.deleteAll()
        iQuestionDao.deleteAll()
    }
}