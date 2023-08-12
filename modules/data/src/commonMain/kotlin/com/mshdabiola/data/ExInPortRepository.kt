package com.mshdabiola.data

import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.instructiondao.IInstructionDao
import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshabiola.database.dao.topicdao.ITopicDao
import com.mshdabiola.data.repository.inter.IExInPortRepository
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first

class ExInPortRepository(
    private val subjectDao: ISubjectDao,
    private val examDao: IExamDao,
    private val questionDao: IQuestionDao,
    private val optionDao: IOptionDao,
    private val instruDao: IInstructionDao,
    private val topicDao: ITopicDao
) : IExInPortRepository {

    /*

    move image to subject folder


    */


    override suspend fun export(
        coroutineScope: CoroutineScope,
        subjectId: Long,
        onFinish: (List<Subject>, List<Exam>, List<QuestionFull>, List<Instruction>, List<Topic>) -> Unit
    ) {
        val subject = coroutineScope.async { subjectDao.getOne(subjectId).first() }
        val exams = coroutineScope.async { examDao.getBySubject(subjectId).first() }

        val listQuestion = mutableListOf<QuestionFull>()
        val listInstruction = mutableListOf<Instruction>()
        exams.await().forEach {

            val questions = coroutineScope.async { questionDao.getAllWithOptions(it.id).first() }
            val options = coroutineScope.async { optionDao.getAllByExamId(it.id).first() }
            val instru = coroutineScope.async { instruDao.getAll(it.id).first() }
            listQuestion.addAll(questions.await())
            listInstruction.addAll(instru.await())

        }

        val topics = coroutineScope.async {
            topicDao
                .getAllBySubject(subjectId)
                .first()
        }

        onFinish(
            listOf(subject.await()),
            exams.await(),
            listQuestion,
            listInstruction,
            topics.await()
        )

    }
}