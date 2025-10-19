/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.InstructionDao
import com.mshdabiola.database.dao.OptionDao
import com.mshdabiola.database.dao.QuestionDao
import com.mshdabiola.database.dao.SeriesDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.dao.TopicCategoryDao
import com.mshdabiola.database.dao.TopicDao
import com.mshdabiola.database.dao.UserDao
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.PaperEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.SessionExamination
import com.mshdabiola.database.model.SessionQuestion
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.TopicCategoryEntity
import com.mshdabiola.database.model.TopicEntity
import com.mshdabiola.database.model.UserEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SeriesDatabaseCtor : RoomDatabaseConstructor<SeriesDatabase>

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
@ConstructedBy(SeriesDatabaseCtor::class) // NEW
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
