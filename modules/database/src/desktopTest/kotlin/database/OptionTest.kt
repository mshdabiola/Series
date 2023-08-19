package database

import com.mshdabiola.database.SeriesDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class OptionTest : AbstractTest() {

    override fun insert() = runTest {

        println(SeriesDatabase.Schema.version)

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