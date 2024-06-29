package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.database.di.daoModules
import com.mshdabiola.database.di.getDatabaseBuilder
import com.mshdabiola.database.di.getRoomDatabase
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.io.File

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single(qualifier = qualifier("per")) {
                val db = Room
                    .inMemoryDatabaseBuilder<SeriesDatabase>()
                    .setDriver(BundledSQLiteDriver())
                getRoomDatabase(db)
            }
            factory(qualifier = qualifier(name = "tem")) { parameters ->
                val path: String = parameters[0]
                val dbFile = File(path) // File(System.getProperty("java.io.tmpdir"), Constant.databaseName)
                getRoomDatabase(getDatabaseBuilder(dbFile))
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
