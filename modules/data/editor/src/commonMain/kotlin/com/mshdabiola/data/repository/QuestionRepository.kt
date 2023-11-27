package com.mshdabiola.data.repository

import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.toQuestion
import kotlinx.coroutines.flow.Flow

internal class QuestionRepository(
    private val iQuestionDao: IQuestionDao,
    private val iOptionDao: IOptionDao,
) : IQuestionRepository {

    override suspend fun insert(questionFull: QuestionFull) {

        if (questionFull.id == -1L) {
            val id = (iQuestionDao.getMaxId() ?: 0) + 1
            println("id $id")
            iQuestionDao.insert(questionFull.copy(id = id).toQuestion())
            iOptionDao.insertMany(questionFull.options.map { it.copy(questionId = id) })

        } else {

            iQuestionDao.insert(questionFull.toQuestion())
            iOptionDao.insertMany(questionFull.options)

        }


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

        iQuestionDao.delete(id)


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

    override suspend fun deleteOption(id: Long) {
        iOptionDao.delete(id)
    }
}