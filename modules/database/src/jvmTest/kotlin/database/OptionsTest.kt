package database

import com.mshdabiola.database.dao.exam.OptionDao
import org.junit.Test
import org.koin.core.component.inject

class OptionsTest :AbstractTest() {

    val optionDao by inject<OptionDao>()

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