package database

import com.mshabiola.database.SaveDatabase
import com.mshabiola.database.dao.exam.ExamDao
import com.mshabiola.database.dao.optiondao.OptionDao
import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.dao.subjectdao.SubjectDao
import com.mshabiola.database.di.name
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.core.qualifier.qualifier
import org.koin.test.inject
import kotlin.io.path.Path
import kotlin.reflect.jvm.internal.impl.metadata.ProtoBuf.QualifiedNameTable.QualifiedName
import kotlin.test.assertEquals

class SaveDbTest : AbstractTest() {


    override fun insert() = runTest {
        val questionDao by inject<QuestionDao>()
        val optionDao by inject<OptionDao>()
        val subjDao by inject<SubjectDao>()
        val examDao by inject<ExamDao>()

        val inputDb by inject<SeriesDatabase>(qualifier = qualifier(name))
        val db= SaveDatabase()

        subjDao.insert(Subject(1, "Math"))
        subjDao.insert(Subject(2, "English"))

        examDao.insert(Exam(1, 1, false,2012))
        examDao.insert(Exam(2, 2, false,2013))
        examDao.insert(Exam(3, 1, false,2014))
        examDao.insert(Exam(4, 2, false,2015))
        questionDao.insert(
            Question(
                id = 1,
                nos = 2,
                examId = 1,
                content = listOf(Item("What is your name")),
                isTheory = false,
                answer = null,
                instructionId = null,
                topicId = null
            )
        )
        questionDao.insert(
            Question(
                id = 2,
                nos = 2,
                examId = 2,
                content = listOf(Item("What is your name")),
                isTheory = false,
                answer = null,
                instructionId = null,
                topicId = null
            )
        )
        questionDao.insert(
            Question(
                id = 3,
                nos = 2,
                examId = 1,
                content = listOf(Item("What is your name")),
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
                    examId = 1,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                ),
                Option(
                    id = -1,
                    nos = 2,
                    questionId = 1,
                    examId = 1,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                ),
                Option(
                    id = -1,
                    nos = 3,
                    questionId = 1,
                    examId = 1,
                    content = listOf(Item("abioa")),
                    isAnswer = false
                )
            )
        )

        db.insert(inputDb,
            listOf(1,2,3,4),
            "/Users/user/AndroidStudioProjects/Series/subject/test.db")



    }

    override fun delete() = runTest {

    }

    override fun getOne() = runTest {
        TODO("Not yet implemented")
    }

    override fun getAll() = runTest {
        TODO("Not yet implemented")
    }

    @Test
    fun update() = runTest {


    }

}

