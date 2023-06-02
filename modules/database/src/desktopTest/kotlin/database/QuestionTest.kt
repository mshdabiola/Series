package database

import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Question
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
        TODO("Not yet implemented")
    }

    override fun getOne() = runTest {
        TODO("Not yet implemented")
    }

    override fun getAll() = runTest {
        TODO("Not yet implemented")
    }

}