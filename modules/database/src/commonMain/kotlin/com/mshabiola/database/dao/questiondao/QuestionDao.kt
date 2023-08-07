package com.mshabiola.database.dao.questiondao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import app.cash.sqldelight.coroutines.mapToOne
import com.mshabiola.database.model.toEntity
import com.mshabiola.database.model.toModel
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.toQuestionWithOptions
import commshdabioladatabase.tables.InstructionQueries
import commshdabioladatabase.tables.OptionQueries
import commshdabioladatabase.tables.QuestionQueries
import commshdabioladatabase.tables.TopicQueries
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class QuestionDao(
    private val questionQueries: QuestionQueries,
    private val optionQueries: OptionQueries,
    private val instructionQueries: InstructionQueries,
    private val topicQueries: TopicQueries,
    private val coroutineDispatcher: CoroutineDispatcher
) : IQuestionDao {
    override suspend fun insert(question: Question) {
        withContext(coroutineDispatcher) {
            if (question.id == -1L)
                questionQueries.insert(question.toEntity())
            else
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

    override fun getWithExamId(examId: Long): Flow<List<Question>> {
        return questionQueries
            .getAllWithExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
    }

    override fun getAllWithOptions(examId: Long): Flow<List<QuestionFull>> {
      val flow1=  questionQueries
            .getAllWithExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
            .flowOn(coroutineDispatcher)
        val flow2=optionQueries.getAllByExamId(examId)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }

      return  combine(flow1,flow2){ questions, options->
            questions.map {question->
                val inst = question.instructionId?.let { it1 ->
                    instructionQueries
                        .getById(it1)
                        .executeAsOne()
                        .toModel()
                }
                val topic = question.topicId?.let {
                    topicQueries
                        .getById(it)
                        .executeAsOne()
                        .toModel()
                }

               val option= options.filter { it.questionId==question.id }
                question.toQuestionWithOptions(option, inst, topic)
            }


        }
          .flowOn(coroutineDispatcher)

    }

    override fun getRandom(num: Long): Flow<List<QuestionFull>> {
        return questionQueries
            .getRandom(num)
            .asFlow()
            .mapToList(coroutineDispatcher)
            .map { it.map { it.toModel() } }
            .map { questionList ->
                questionList.map { question ->

                    val list = optionQueries
                        .getAllWithQuestionNo(question.nos, question.examId)
                        .executeAsList()
                        .map { it.toModel() }
                    val inst = question.instructionId?.let { it1 ->
                        instructionQueries
                            .getById(it1)
                            .executeAsOne()
                            .toModel()
                    }
                    val topic = question.topicId?.let {
                        topicQueries
                            .getById(it)
                            .executeAsOne()
                            .toModel()
                    }
                    question.toQuestionWithOptions(list, inst, topic)
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

    override suspend fun deleteAll() {
        withContext(coroutineDispatcher) {
            questionQueries.deleteAll()
        }
    }

    override suspend fun getMaxId(): Long? {
        return questionQueries.getMaxId().executeAsOne().max
    }
}