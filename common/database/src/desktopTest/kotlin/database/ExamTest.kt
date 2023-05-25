package database

import app.cash.turbine.test
import com.mshabiola.database.dao.exam.ExamDao
import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Question
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExamTest : AbstractTest() {


    override fun insert() = runTest {
        val examDao by inject<ExamDao>()

        examDao.insert(
            Exam(id = 6100L, subjectID = 2153L, year = 659L)
        )
        examDao.insert(
            Exam(id = 6100L, subjectID = 232, year = 3)
        )
        examDao.insert(
            Exam(subjectID = 232, year = 3)
        )

        examDao.getAll()
            .test {
                val list = awaitItem()
                println(list.joinToString())
                assertEquals(2, list.size)
                this.cancelAndIgnoreRemainingEvents()

            }


    }

    override fun delete() = runTest {

    }

    override fun getOne() = runTest {
        TODO("Not yet implemented")
    }

    override fun getAll() = runTest {
        TODO("Not yet implemented")
    }

}