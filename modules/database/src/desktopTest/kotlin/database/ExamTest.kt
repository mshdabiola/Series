package database

import app.cash.turbine.test
import com.mshabiola.database.dao.exam.ExamDao
import com.mshdabiola.model.data.Exam
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.test.inject
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExamTest : AbstractTest() {

    override fun insert() = runTest {
        val examDao by inject<ExamDao>()

        examDao.insert(
            Exam(id = 6100L, subjectID = 2153L, year = 659L, isObjOnly = false, examTime = 8),
        )
        examDao.insert(
            Exam(id = 6100L, subjectID = 232, year = 3, isObjOnly = false, examTime = 8),
        )
        examDao.insert(
            Exam(subjectID = 232, year = 3, isObjOnly = false, examTime = 8),
        )

        examDao.getAll()
            .test {
                val list = awaitItem()
                println(list.joinToString())
                assertEquals(3, list.size)
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
