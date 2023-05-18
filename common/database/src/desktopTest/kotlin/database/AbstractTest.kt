package database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.di.daoModules
import com.mshdabiola.database.SeriesDatabase
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single {
                val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
                    .also { SeriesDatabase.Schema.create(it) }

                SeriesDatabase(driver)
            }

        }
        // Your KoinApplication instance here
        modules(module, daoModules)

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