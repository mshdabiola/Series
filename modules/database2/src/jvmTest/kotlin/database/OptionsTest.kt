package database

import com.mshdabiola.seriesdatabase.dao.OptionDao
import org.junit.Test
import org.koin.core.component.inject

class OptionsTest : AbstractTest() {

    val optionDao by inject<OptionDao>()

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
