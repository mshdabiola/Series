/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.dao.AcademicStaffDao
import com.mshdabiola.seriesdatabase.dao.ChoiceOptionDao
import com.mshdabiola.seriesdatabase.dao.ClassAttendanceDao
import com.mshdabiola.seriesdatabase.dao.ClassDao
import com.mshdabiola.seriesdatabase.dao.CourseDao
import com.mshdabiola.seriesdatabase.dao.CourseGradeDao
import com.mshdabiola.seriesdatabase.dao.ExamAttendanceDao
import com.mshdabiola.seriesdatabase.dao.ExamPaperDao
import com.mshdabiola.seriesdatabase.dao.ExamQuestionDao
import com.mshdabiola.seriesdatabase.dao.ExamScheduleDao
import com.mshdabiola.seriesdatabase.dao.GradeLevelDao
import com.mshdabiola.seriesdatabase.dao.LearningMaterialDao
import com.mshdabiola.seriesdatabase.dao.LearningObjectiveDao
import com.mshdabiola.seriesdatabase.dao.LessonTopicDao
import com.mshdabiola.seriesdatabase.dao.SchoolDao
import com.mshdabiola.seriesdatabase.dao.StudentAnswerDao
import com.mshdabiola.seriesdatabase.dao.StudentAnswerSheetDao
import com.mshdabiola.seriesdatabase.dao.StudentDao
import com.mshdabiola.seriesdatabase.dao.TeacherCourseQualificationDao
import com.mshdabiola.seriesdatabase.model.AcademicStaffEntity
import com.mshdabiola.seriesdatabase.model.ChoiceOptionEntity
import com.mshdabiola.seriesdatabase.model.ClassAttendanceEntity
import com.mshdabiola.seriesdatabase.model.ClassEntity
import com.mshdabiola.seriesdatabase.model.CourseEntity
import com.mshdabiola.seriesdatabase.model.CourseGradeEntity
import com.mshdabiola.seriesdatabase.model.ExamAttendanceEntity
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
import com.mshdabiola.seriesdatabase.model.TeacherCourseQualificationEntity
import com.mshdabiola.seriesdatabase.util.Converters

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object SchoolDatabaseCtor : RoomDatabaseConstructor<SchoolDatabase>

@Database(
    entities = [
        SchoolEntity::class,
        AcademicStaffEntity::class,
        GradeLevelEntity::class,
        ClassEntity::class,
        StudentEntity::class,
        CourseEntity::class,
        LessonTopicEntity::class,
        LearningMaterialEntity::class,
        LearningObjectiveEntity::class,
        ExamPaperEntity::class,
        ExamScheduleEntity::class,
        ExamQuestionEntity::class,
        ChoiceOptionEntity::class,
        StudentAnswerSheetEntity::class,
        StudentAnswerEntity::class,
        CourseGradeEntity::class,
        ClassAttendanceEntity::class,
        ExamAttendanceEntity::class,
        TeacherCourseQualificationEntity::class,
    ],
    version = 1, // Increment version if you modify schema later
    exportSchema = false, // Set to true to export schema for migrations

//    autoMigrations = [
    // AutoMigration(from = 1, to = 2, spec = DatabaseMigrations.Schema1to2::class),
//        AutoMigration(from = 2, to = 3, spec = DatabaseMigrations.Schema2to3::class),
//        AutoMigration(from = 3, to = 4, spec = DatabaseMigrations.Schema2to3::class),

//    ],
)
@ConstructedBy(SchoolDatabaseCtor::class) // NEW
@TypeConverters(Converters::class) // Register your Converters class
abstract class SchoolDatabase : RoomDatabase() {

    // Declare DAOs as abstract properties (Room will provide implementation)
    abstract fun schoolDao(): SchoolDao
    abstract fun academicStaffDao(): AcademicStaffDao
    abstract fun gradeLevelDao(): GradeLevelDao
    abstract fun classDao(): ClassDao
    abstract fun studentDao(): StudentDao
    abstract fun courseDao(): CourseDao
    abstract fun lessonTopicDao(): LessonTopicDao
    abstract fun learningMaterialDao(): LearningMaterialDao
    abstract fun learningObjectiveDao(): LearningObjectiveDao
    abstract fun examPaperDao(): ExamPaperDao
    abstract fun examScheduleDao(): ExamScheduleDao
    abstract fun examQuestionDao(): ExamQuestionDao
    abstract fun choiceOptionDao(): ChoiceOptionDao
    abstract fun studentAnswerSheetDao(): StudentAnswerSheetDao
    abstract fun studentAnswerDao(): StudentAnswerDao
    abstract fun courseGradeDao(): CourseGradeDao
    abstract fun classAttendanceDao(): ClassAttendanceDao
    abstract fun examAttendanceDao(): ExamAttendanceDao
    abstract fun teacherCourseQualificationDao(): TeacherCourseQualificationDao

    // You can add more helper functions or database-wide logic here if needed
}
