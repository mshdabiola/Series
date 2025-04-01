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
        get<SchoolDatabase>(qualifier = qualifier("per")).academicStaffDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).choiceOptionDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).classDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).classAttendanceDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).courseDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).examAttendanceDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).examPaperDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).examQuestionDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).examScheduleDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).gradeLevelDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).learningMaterialDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).learningObjectiveDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).lessonTopicDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).schoolDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).studentAnswerDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).studentAnswerSheetDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).studentDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).teacherCourseQualificationDao()
    }
    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).examInstructionDao()
    }

    single {
        get<SchoolDatabase>(qualifier = qualifier("per")).courseGradeDao()
    }

    single {
        ExportImport(get(qualifier = qualifier("per")))
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
