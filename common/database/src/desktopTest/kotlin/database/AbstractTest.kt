package database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.di.daoModules
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single(qualifier = qualifier("temp")) {
                val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
                    .also { SeriesDatabase.Schema.create(it) }

                SeriesDatabase(
                    driver = driver,
                    questionEntityAdapter = QuestionEntity.Adapter(listOfValueAdapter),
                    instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                    optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
                )
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