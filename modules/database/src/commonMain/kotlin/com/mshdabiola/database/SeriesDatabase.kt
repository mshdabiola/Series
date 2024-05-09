/*
 *abiola 2024
 */

package com.mshdabiola.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.mshdabiola.database.converter.ContentListConverter
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.ImageDao
import com.mshdabiola.database.dao.InstructionDao
import com.mshdabiola.database.dao.NoteDao
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.dao.TopicDao
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.ImageEntity
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.NoteEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.TopicEntity

@Database(
    entities =
    [NoteEntity::class,
        ImageEntity::class,
        ExaminationEntity::class,
        InstructionEntity::class,
        OptionEntity::class,
        QuestionEntity::class,
        SubjectEntity::class,
        TopicEntity::class
    ],
    version = 1,
//    autoMigrations = [
//        //AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//
//                     ]
//    ,
    exportSchema = true,
)
@TypeConverters(ContentListConverter::class)
abstract class SeriesDatabase : RoomDatabase() {

    abstract fun getNoteDao(): NoteDao

    abstract fun getImageDao(): ImageDao

    abstract fun getExaminationDao(): ExaminationDao

    abstract fun getInstructionDao(): InstructionDao

    abstract fun getOptionDao(): OptionDao

    abstract fun getQuestionDao(): QuestionDao

    abstract fun getSubjectDao(): SubjectDao

    abstract fun getTopicDao(): TopicDao


//
//    abstract fun getPlayerDao(): PlayerDao
//
//    abstract fun getPawnDao(): PawnDao
}
