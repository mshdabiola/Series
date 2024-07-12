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
import com.mshdabiola.database.dao.exam.ExaminationDao
import com.mshdabiola.database.dao.exam.InstructionDao
import com.mshdabiola.database.dao.exam.OptionDao
import com.mshdabiola.database.dao.exam.QuestionDao
import com.mshdabiola.database.dao.exam.SubjectDao
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.UserEntity
import com.mshdabiola.database.model.exam.ExaminationEntity
import com.mshdabiola.database.model.exam.InstructionEntity
import com.mshdabiola.database.model.exam.OptionEntity
import com.mshdabiola.database.model.exam.QuestionEntity
import com.mshdabiola.database.model.exam.SubjectEntity
import com.mshdabiola.database.model.topic.TopicEntity

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
    ],
    version = 3,
    autoMigrations = [
        AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),

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
//
//    abstract fun getPlayerDao(): PlayerDao
//
//    abstract fun getPawnDao(): PawnDao
}
