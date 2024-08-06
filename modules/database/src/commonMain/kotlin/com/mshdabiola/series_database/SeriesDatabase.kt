/*
 *abiola 2024
 */

package com.mshdabiola.series_database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mshdabiola.series_database.dao.ExaminationDao
import com.mshdabiola.series_database.dao.InstructionDao
import com.mshdabiola.series_database.dao.OptionDao
import com.mshdabiola.series_database.dao.QuestionDao
import com.mshdabiola.series_database.dao.SeriesDao
import com.mshdabiola.series_database.dao.SubjectDao
import com.mshdabiola.series_database.dao.TopicCategoryDao
import com.mshdabiola.series_database.dao.TopicDao
import com.mshdabiola.series_database.dao.UserDao
import com.mshdabiola.series_database.model.ExaminationEntity
import com.mshdabiola.series_database.model.InstructionEntity
import com.mshdabiola.series_database.model.OptionEntity
import com.mshdabiola.series_database.model.PaperEntity
import com.mshdabiola.series_database.model.QuestionEntity
import com.mshdabiola.series_database.model.SeriesEntity
import com.mshdabiola.series_database.model.SessionExamination
import com.mshdabiola.series_database.model.SessionQuestion
import com.mshdabiola.series_database.model.SubjectEntity
import com.mshdabiola.series_database.model.TopicCategoryEntity
import com.mshdabiola.series_database.model.TopicEntity
import com.mshdabiola.series_database.model.UserEntity

@Database(
    entities =
    [
        UserEntity::class,
        SeriesEntity::class,
        ExaminationEntity::class,
        InstructionEntity::class,
        OptionEntity::class,
        QuestionEntity::class,
        SubjectEntity::class,
        TopicEntity::class,
        TopicCategoryEntity::class,
        SessionExamination::class,
        PaperEntity::class,
        SessionQuestion::class,
    ],
    version = 1,
    autoMigrations = [
        //AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
//        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//        AutoMigration(from = 3, to = 4, spec = DatabaseMigrations.Schema2to3::class),

    ],
    exportSchema = true,
)
abstract class SeriesDatabase : RoomDatabase() {

    abstract fun getExaminationDao(): ExaminationDao

    abstract fun getInstructionDao(): InstructionDao

    abstract fun getOptionDao(): OptionDao

    abstract fun getQuestionDao(): QuestionDao

    abstract fun getSubjectDao(): SubjectDao

    abstract fun getTopicDao(): TopicDao

    abstract fun getSeriesDao(): SeriesDao

    abstract fun getUserDao(): UserDao

    abstract fun getTopicCategoryDao(): TopicCategoryDao
}
