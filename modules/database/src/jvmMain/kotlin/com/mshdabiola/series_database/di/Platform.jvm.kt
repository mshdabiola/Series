package com.mshdabiola.series_database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshabiola.database.util.Constant
import com.mshdabiola.series_database.SeriesDatabase
import com.mshdabiola.series_database.generalPath
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier(name = "per")) {
            val dbFile = File(
                generalPath,
                Constant.databaseName,
            ) // File(System.getProperty("java.io.tmpdir"), Constant.databaseName)
            getRoomDatabase(getDatabaseBuilder(dbFile))
        }
        factory(qualifier = qualifier(name = "tem")) { parameters ->
            val path: String = parameters[0]
            val dbFile = File(path) // File(System.getProperty("java.io.tmpdir"), Constant.databaseName)
            getRoomDatabase(getDatabaseBuilder(dbFile))
        }
        includes(daoModules)
    }

fun getDatabaseBuilder(path: File): RoomDatabase.Builder<SeriesDatabase> {
    return Room.databaseBuilder<SeriesDatabase>(
        name = path.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}
