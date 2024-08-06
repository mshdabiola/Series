package com.mshdabiola.series_database.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mshabiola.database.util.Constant
import com.mshdabiola.series_database.SeriesDatabase
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier(name = "per")) {
            val context: Context = get()
            val appContext = context.applicationContext
            val dbFile = appContext.getDatabasePath(Constant.databaseName)

            getRoomDatabase(getDatabaseBuilder(appContext, dbFile))
        }
        factory(qualifier = qualifier(name = "tem")) { parameters ->
            val path: String = parameters[0]
            val dbFile = File(path) // File(System.getProperty("java.io.tmpdir"), Constant.databaseName)

            val context: Context = get()
            val appContext = context.applicationContext

            getRoomDatabase(getDatabaseBuilder(appContext, dbFile))
        }

        includes(daoModules)
    }

fun getDatabaseBuilder(appContext: Context, path: File): RoomDatabase.Builder<SeriesDatabase> {
    return Room.databaseBuilder<SeriesDatabase>(
        context = appContext,
        name = path.absolutePath,
    )
}
