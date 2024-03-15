package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow

interface IQuestionRepository {

    fun getAllWithExamId(examId: Long): Flow<List<QuestionFull>>
    fun getRandom(num: Long): Flow<List<QuestionFull>>
}
