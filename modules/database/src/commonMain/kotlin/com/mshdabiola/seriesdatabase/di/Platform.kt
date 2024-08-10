package com.mshdabiola.seriesdatabase.di

import androidx.room.RoomDatabase
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshabiola.database.util.Constant
import com.mshdabiola.seriesdatabase.ExportImport
import com.mshdabiola.seriesdatabase.SeriesDatabase
import com.mshdabiola.seriesdatabase.callback
import com.mshdabiola.seriesdatabase.generalPath
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
         .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
