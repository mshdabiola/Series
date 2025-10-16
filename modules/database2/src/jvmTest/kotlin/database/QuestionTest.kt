package database

import com.mshdabiola.seriesdatabase.dao.QuestionDao
import org.junit.Test
import org.koin.core.component.inject

class QuestionTest : AbstractTest() {

    val questionDao by inject<QuestionDao>()

    @Test
    override fun insert() {
    }

    @Test
    override fun delete() {
    }

    @Test
    override fun getOne() {
    }

    @Test
    override fun getAll() {
    }
}
