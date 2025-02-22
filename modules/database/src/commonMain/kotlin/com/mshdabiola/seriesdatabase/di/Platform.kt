package com.mshdabiola.seriesdatabase.di

import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.seriesdatabase.ExportImport
import com.mshdabiola.seriesdatabase.SchoolDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

expect val databaseModule: Module

val daoModules = module {

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getExaminationDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getInstructionDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getOptionDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getQuestionDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getSubjectDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getTopicDao()
    }
//    single {
//        DatabaseExportImport(get(qualifier = qualifier("per")))
//    }

    single {
        ExportImport(get(qualifier = qualifier("per")))
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getSeriesDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getUserDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).getTopicCategoryDao()
    }
}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<SchoolDatabase>,
): SchoolDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}
