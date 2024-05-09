package com.mshdabiola.database.di

import androidx.room.RoomDatabase
import com.mshdabiola.database.SeriesDatabase
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.dsl.module

expect val databaseModule: Module

val daoModules = module {
    single {
        get<SeriesDatabase>().getNoteDao()
    }

    single {
        get<SeriesDatabase>().getImageDao()
    }

    single {
        get<SeriesDatabase>().getExaminationDao()
    }

    single {
        get<SeriesDatabase>().getInstructionDao()
    }
    single {
        get<SeriesDatabase>().getOptionDao()
    }
    single {
        get<SeriesDatabase>().getQuestionDao()
    }
    single {
        get<SeriesDatabase>().getSubjectDao()
    }
    single {
        get<SeriesDatabase>().getTopicDao()
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
        .build()
}
