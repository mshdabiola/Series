package com.mshdabiola.database.di

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.DatabaseExportImport
import com.mshdabiola.database.ExportImport
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.database.callback
import com.mshdabiola.database.generalPath
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.io.File

expect val databaseModule: Module

val daoModules = module {

    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getExaminationDao()
    }

    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getInstructionDao()
    }
    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getOptionDao()
    }
    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getQuestionDao()
    }
    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getSubjectDao()
    }
    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getTopicDao()
    }
//    single {
//        DatabaseExportImport(get(qualifier = qualifier("per")))
//    }

    single {
        ExportImport(get(qualifier = qualifier("per")))
    }

    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getSeriesDao()
    }

    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getUserDao()
    }

    single {
        get<SeriesDatabase>(qualifier = qualifier("per")).getTopicCategoryDao()
    }

}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<SeriesDatabase>,
): SeriesDatabase {
    return builder
        // .addMigrations(MIGRATIONS)
        .fallbackToDestructiveMigrationOnDowngrade(false)
        // .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .addCallback(object : RoomDatabase.Callback() {
            override fun onCreate(connection: SQLiteConnection) {
                callback?.onCreate(connection, File(generalPath, Constant.databaseName).path)
                super.onCreate(connection)
            }

            override fun onOpen(connection: SQLiteConnection) {
                callback?.onOpen(connection)
                super.onOpen(connection)
            }

            override fun onDestructiveMigration(connection: SQLiteConnection) {
                callback?.onDestructiveMigration(connection)
                super.onDestructiveMigration(connection)
            }
        })
        .build()
}
