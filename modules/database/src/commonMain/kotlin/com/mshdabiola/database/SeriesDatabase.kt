/*
 *abiola 2024
 */

package com.mshdabiola.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.mshdabiola.database.dao.SeriesDao
import com.mshdabiola.database.dao.TopicDao
import com.mshdabiola.database.dao.UserDao
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.InstructionDao
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.dao.TopicCategoryDao
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.UserEntity
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.PaperEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.SessionExamination
import com.mshdabiola.database.model.SessionQuestion
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.TopicCategoryEntity
import com.mshdabiola.database.model.TopicEntity

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
        SessionQuestion::class
    ],
    version = 2,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
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
