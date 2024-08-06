package database

import com.mshdabiola.series_testing.databaseTestModule
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(databaseTestModule)
    }

    @Test
    abstract fun insert()

    @Test
    abstract fun delete()

    @Test
    abstract fun getOne()

    @Test
    abstract fun getAll()
}
