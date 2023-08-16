package database

import com.mshabiola.database.dao.exam.ExamDao
import com.mshabiola.database.dao.optiondao.OptionDao
import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.dao.subjectdao.SubjectDao
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class QuestionTest : AbstractTest() {
    @Test
    fun text() {
        val qvalue = listOf(Item("abiola"), Item("Moshood"))
        val str = listOfValueAdapter.encode(qvalue)

        println(str)
        val list = listOfValueAdapter.decode(str)
        println(list)

        assertEquals(qvalue, list)
    }

    override fun insert() = runTest {
        val questionDao by inject<QuestionDao>()
        questionDao.insert(
            Question(
                nos = 4290L,
                examId = 5013L,
                content = listOf(Item("abioa")),
                isTheory = true,
                answer = null,
                instructionId = null,
                topicId = null
            )
        )

        assertEquals(1, questionDao.getAll().first().size)
    }

    override fun delete() = runTest {
        val questionDao by inject<QuestionDao>()
        val optionDao by inject<OptionDao>()
        val subjDao by inject<SubjectDao>()
        val examDao by inject<ExamDao>()

        subjDao.insert(Subject(1, "Math"))

        examDao.insert(Exam(9, 1, false,2012))

        questionDao.insert(
            Question(
                id = 1,
                nos = 2,
                examId = 9L,
                content = listOf(Item("abioa")),
                isTheory = false,
                answer = null,
                instructionId = null,
                topicId = null
            )
        )
        optionDao.insertMany(
            listOf(
                Option(
                    id = -1,
                    nos = 1,
                    questionId = 1,
                    examId = 9,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                ),
                Option(
                    id = -1,
                    nos = 2,
                    questionId = 1,
                    examId = 9,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                ),
                Option(
                    id = -1,
                    nos = 3,
                    questionId = 1,
                    examId = 9,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                )
            )
        )

        questionDao.delete(1)
        val all =
            questionDao.getAllWithOptions(9).first()

        println("all ${all.joinToString()}")
        assertEquals(0, all.size)
        assertEquals(1, optionDao.getAllByQuestionIdAndExamId(1, 9).first().size)
    }

    override fun getOne() = runTest {
        TODO("Not yet implemented")
    }

    override fun getAll() = runTest {
        TODO("Not yet implemented")
    }

    @Test
    fun update() = runTest {
        val questionDao by inject<QuestionDao>()
        val optionDao by inject<OptionDao>()
        val subjDao by inject<SubjectDao>()
        val examDao by inject<ExamDao>()

        subjDao.insert(Subject(1, "Math"))

        examDao.insert(Exam(9, 1, false,2012))

        questionDao.insert(
            Question(
                id = 1,
                nos = 2,
                examId = 9L,
                content = listOf(Item("abioa")),
                isTheory = false,
                answer = null,
                instructionId = null,
                topicId = null
            )
        )
        var list = listOf(
            Option(
                id = -1,
                nos = 1,
                questionId = 1,
                examId = 9,
                content = listOf(Item("abioa")),
                isAnswer = false
            ),
            Option(
                id = -1,
                nos = 2,
                questionId = 1,
                examId = 9,
                content = listOf(Item("abioa")),
                isAnswer = false
            ),
            Option(
                id = -1,
                nos = 3,
                questionId = 1,
                examId = 9,
                content = listOf(Item("abioa")),
                isAnswer = false
            )
        )
        optionDao.insertMany(
            list
        )
        list = optionDao.getAllByQuestionIdAndExamId(1, 9).first().toMutableList()
        list.removeAt(0)
        optionDao.insertMany(list)

        val all =
            questionDao.getAllWithOptions(9).first()

        println("all ${all.joinToString()}")
        assertEquals(1, all.size)
        assertEquals(1, optionDao.getAllByQuestionIdAndExamId(1, 9).first().size)

    }

}