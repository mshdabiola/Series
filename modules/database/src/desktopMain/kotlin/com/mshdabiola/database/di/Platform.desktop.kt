package com.mshdabiola.database.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() = module {
        single {
            getRoomDatabase(getDatabaseBuilder())
        }
        includes(daoModules)
    }

fun getDatabaseBuilder(): RoomDatabase.Builder<SeriesDatabase> {
    val dbFile = File(
        generalPath,
        Constant.databaseName,
    ) // File(System.getProperty("java.io.tmpdir"), Constant.databaseName)
    return Room.databaseBuilder<SeriesDatabase>(
        name = dbFile.absolutePath,
    )
        .setDriver(BundledSQLiteDriver())
}
