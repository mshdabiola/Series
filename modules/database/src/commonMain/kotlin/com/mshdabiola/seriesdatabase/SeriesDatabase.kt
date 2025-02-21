/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.mshdabiola.seriesdatabase.dao.ExaminationDao
import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesdatabase.dao.OptionDao
import com.mshdabiola.seriesdatabase.dao.QuestionDao
import com.mshdabiola.seriesdatabase.dao.SeriesDao
import com.mshdabiola.seriesdatabase.dao.SubjectDao
import com.mshdabiola.seriesdatabase.dao.TopicCategoryDao
import com.mshdabiola.seriesdatabase.dao.TopicDao
import com.mshdabiola.seriesdatabase.dao.UserDao
import com.mshdabiola.seriesdatabase.model.AcademicStaffEntity
import com.mshdabiola.seriesdatabase.model.ChoiceOptionEntity
import com.mshdabiola.seriesdatabase.model.ClassAttendanceEntity
import com.mshdabiola.seriesdatabase.model.ClassEntity
import com.mshdabiola.seriesdatabase.model.CourseEntity
import com.mshdabiola.seriesdatabase.model.CourseGradeEntity
import com.mshdabiola.seriesdatabase.model.ExamAttendanceEntity
import com.mshdabiola.seriesdatabase.model.ExamInstructionEntity
import com.mshdabiola.seriesdatabase.model.ExamPaperEntity
import com.mshdabiola.seriesdatabase.model.ExamQuestionEntity
import com.mshdabiola.seriesdatabase.model.ExamScheduleEntity
import com.mshdabiola.seriesdatabase.model.GradeLevelEntity
import com.mshdabiola.seriesdatabase.model.LearningMaterialEntity
import com.mshdabiola.seriesdatabase.model.LearningObjectiveEntity
import com.mshdabiola.seriesdatabase.model.LessonTopicEntity
import com.mshdabiola.seriesdatabase.model.SchoolEntity
import com.mshdabiola.seriesdatabase.model.StudentAnswerEntity
import com.mshdabiola.seriesdatabase.model.StudentAnswerSheetEntity
import com.mshdabiola.seriesdatabase.model.StudentEntity

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SeriesDatabaseCtor : RoomDatabaseConstructor<SeriesDatabase>

@Database(
    entities =
    [
        SchoolEntity::class,
        AcademicStaffEntity::class,
        GradeLevelEntity::class,

        ClassEntity::class,
        StudentEntity::class,
        CourseEntity::class,
        LessonTopicEntity::class,
        LearningObjectiveEntity::class,
        LearningMaterialEntity::class,
        ClassAttendanceEntity::class,

        ExamPaperEntity::class,
        ExamQuestionEntity::class,
        ExamInstructionEntity::class,
        ChoiceOptionEntity::class,
        ExamScheduleEntity::class,
        ExamAttendanceEntity::class,

        StudentAnswerSheetEntity::class,
        StudentAnswerEntity::class,

        CourseGradeEntity::class,
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
