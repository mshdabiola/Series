package com.mshdabiola.testing

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.database.asEntity
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.InstructionDao
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.database.dao.SeriesDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.dao.TopicCategoryDao
import com.mshdabiola.database.dao.TopicDao
import com.mshdabiola.database.dao.UserDao
import com.mshdabiola.database.di.daoModules
import com.mshdabiola.database.di.getRoomDatabase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

val databaseTestModule = module {
    single(qualifier = qualifier("per")) {
        val db = Room
            .inMemoryDatabaseBuilder<SeriesDatabase>()
            .setDriver(BundledSQLiteDriver())
        getRoomDatabase(db)
    }

    includes(daoModules)
}

val defaultData = exportableData
suspend fun KoinComponent.insertData() {
    val userDao by inject<UserDao>()
    val seriesDao by inject<SeriesDao>()
    val subjectDao by inject<SubjectDao>()
    val examinationDao by inject<ExaminationDao>()
    val instructionDao by inject<InstructionDao>()
    val questionDao by inject<QuestionDao>()
    val optionDao by inject<OptionDao>()
    val topicDao by inject<TopicDao>()
    val topicCategoryDao by inject<TopicCategoryDao>()

    userDao.insertAll(defaultData.users.map { it.asEntity() })
    seriesDao.insertAll(defaultData.series.map { it.asEntity() })
    subjectDao.insertAll(defaultData.subjects.map { it.asEntity() })
    examinationDao.insertAll(defaultData.examinations.map { it.asEntity() })
    instructionDao.insertAll(defaultData.instructions.map { it.asEntity() })
    questionDao.insertAll(defaultData.questions.map { it.asEntity() })
    optionDao.insertAll(defaultData.options.map { it.asEntity() })
    topicCategoryDao.insertAll(defaultData.topicCategory.map { it.asEntity() })
    topicDao.insertAll(defaultData.topics.map { it.asEntity() })
}
