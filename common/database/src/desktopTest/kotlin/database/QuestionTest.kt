package database

import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Question
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.koin.test.inject
import kotlin.test.assertEquals

class QuestionTest : AbstractTest() {
    @Test
    fun text() {
        val qvalue = listOf(Item("abiola"), Item("Moshood"))
        val str = listOfValueAdapter.encode(qvalue)

        println(str)
        val qlist = listOfValueAdapter.decode(str)
        println(qlist)

        assertEquals(qvalue, qlist)
    }

    override fun insert() = runTest {
        val questionDao by inject<QuestionDao>()
        questionDao.insert(
            Question(
                id = null,
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

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun getOne() {
        TODO("Not yet implemented")
    }

    override fun getAll() {
        TODO("Not yet implemented")
    }

}