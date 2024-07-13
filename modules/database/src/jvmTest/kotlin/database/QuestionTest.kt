package database

import com.mshdabiola.database.dao.QuestionDao
import org.junit.Test
import org.koin.core.component.inject

class QuestionTest :AbstractTest() {

    val questionDao by inject<QuestionDao>()

    @Test
    override fun insert() {
        TODO("Not yet implemented")
    }

    @Test
    override fun delete() {
        TODO("Not yet implemented")
    }

    @Test
    override fun getOne() {
        TODO("Not yet implemented")
    }

    @Test
    override fun getAll() {
        TODO("Not yet implemented")
    }

}