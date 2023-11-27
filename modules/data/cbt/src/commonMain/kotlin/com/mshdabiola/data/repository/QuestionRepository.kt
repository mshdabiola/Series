package com.mshdabiola.data.repository

import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow

internal class QuestionRepository(
    private val iQuestionDao: IQuestionDao,
    private val iOptionDao: IOptionDao,
) : IQuestionRepository {

    override fun getAllWithExamId(examId: Long): Flow<List<QuestionFull>> {
        return iQuestionDao.getAllWithOptions(examId)
    }

    override fun getRandom(num: Long): Flow<List<QuestionFull>> {
        return iQuestionDao.getRandom(num)
    }
}