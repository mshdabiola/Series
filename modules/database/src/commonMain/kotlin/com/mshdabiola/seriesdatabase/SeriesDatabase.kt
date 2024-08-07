/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mshdabiola.seriesdatabase.dao.ExaminationDao
import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesdatabase.dao.OptionDao
import com.mshdabiola.seriesdatabase.dao.QuestionDao
import com.mshdabiola.seriesdatabase.dao.SeriesDao
import com.mshdabiola.seriesdatabase.dao.SubjectDao
import com.mshdabiola.seriesdatabase.dao.TopicCategoryDao
import com.mshdabiola.seriesdatabase.dao.TopicDao
import com.mshdabiola.seriesdatabase.dao.UserDao
import com.mshdabiola.seriesdatabase.model.ExaminationEntity
import com.mshdabiola.seriesdatabase.model.InstructionEntity
import com.mshdabiola.seriesdatabase.model.OptionEntity
import com.mshdabiola.seriesdatabase.model.PaperEntity
import com.mshdabiola.seriesdatabase.model.QuestionEntity
import com.mshdabiola.seriesdatabase.model.SeriesEntity
import com.mshdabiola.seriesdatabase.model.SessionExamination
import com.mshdabiola.seriesdatabase.model.SessionQuestion
import com.mshdabiola.seriesdatabase.model.SubjectEntity
import com.mshdabiola.seriesdatabase.model.TopicCategoryEntity
import com.mshdabiola.seriesdatabase.model.TopicEntity
import com.mshdabiola.seriesdatabase.model.UserEntity

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
//    autoMigrations = [
    // AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
//        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//        AutoMigration(from = 3, to = 4, spec = DatabaseMigrations.Schema2to3::class),

//    ],
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
