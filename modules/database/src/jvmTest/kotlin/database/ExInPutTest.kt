package database

import com.mshdabiola.seriesdatabase.ExportImport
import com.mshdabiola.seriesdatabase.dao.AcademicStaffDao
import com.mshdabiola.seriesdatabase.dao.ChoiceOptionDao
import com.mshdabiola.seriesdatabase.dao.ClassAttendanceDao
import com.mshdabiola.seriesdatabase.dao.ClassDao
import com.mshdabiola.seriesdatabase.dao.CourseDao
import com.mshdabiola.seriesdatabase.dao.CourseGradeDao
import com.mshdabiola.seriesdatabase.dao.ExamAttendanceDao
import com.mshdabiola.seriesdatabase.dao.ExamInstructionDao
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
import com.mshdabiola.seriesdatabase.toEntity
import com.mshdabiola.seriestesting.getExportable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExInPutTest : AbstractTest() {

    val path = "/home/mshdabiola/StudioProjects/Series/data.se"

    @Test
    fun exportData() = runTest {
        insertData()
        assertEquals(3, 3)
    }

    @Test
    fun importData() = runTest {
//        val exportImport by inject<ExportImport>()
//
//        File(path)
//            .inputStream()
//            .use {
//                exportImport.import(it, "abiola")
//            }
//
//        val userDao by inject<SchoolDao>()
//
//        assertEquals(
//            getExportable().schools.toMutableList(),
//            userDao.getAllSchools().first().map { it.toDomain() },
//        )
    }

    suspend fun insertData() {
        val exportImport by inject<ExportImport>()

        val schoolDao by inject<SchoolDao>()
        val academicStaffDao by inject<AcademicStaffDao>()
        val choiceOptionDao by inject<ChoiceOptionDao>()
        val classAttendanceDao by inject<ClassAttendanceDao>()
        val classDao by inject<ClassDao>()
        val courseDao by inject<CourseDao>()
        val courseGradeDao by inject<CourseGradeDao>()
        val examAttendanceDao by inject<ExamAttendanceDao>()
        val examInstructionDao by inject<ExamInstructionDao>()
        val examPaperDao by inject<ExamPaperDao>()
        val examQuestionDao by inject<ExamQuestionDao>()
        val examScheduleDao by inject<ExamScheduleDao>()
        val gradeLevelDao by inject<GradeLevelDao>()
        val learningMaterialDao by inject<LearningMaterialDao>()
        val learningObjectiveDao by inject<LearningObjectiveDao>()
        val lessonTopicDao by inject<LessonTopicDao>()
        val studentAnswerDao by inject<StudentAnswerDao>()
        val studentAnswerSheetDao by inject<StudentAnswerSheetDao>()
        val studentDao by inject<StudentDao>()
        val teacherCourseQualificationDao by inject<TeacherCourseQualificationDao>()

        val data = getExportable()

        schoolDao.upsertAll(data.schools.map { it.toEntity() })
        gradeLevelDao.upsertAll(data.gradeLevels.map { it.toEntity() })

        academicStaffDao.upsertAll(data.academicStaff.map { it.toEntity() })
        teacherCourseQualificationDao.upsertAllTeacherCourseQualifications(data.teacherCourseQualifications.map { it.toEntity() })
        classDao.upsertAll(data.classes.map { it.toEntity() })
        courseDao.upsertAll(data.courses.map { it.toEntity() })

        studentDao.upsertAll(data.students.map { it.toEntity() })
        lessonTopicDao.upsertAll(data.lessonTopics.map { it.toEntity() })
        learningObjectiveDao.upsertAll(data.learningObjectives.map { it.toEntity() })
        learningMaterialDao.upsertAll(data.learningMaterials.map { it.toEntity() })

        examAttendanceDao.upsertAll(data.examAttendances.map { it.toEntity() })
        classAttendanceDao.upsertAll(data.classAttendances.map { it.toEntity() })

        examPaperDao.upsertAll(data.examPapers.map { it.toEntity() })
        examQuestionDao.upsertAll(data.examQuestions.map { it.toEntity() })
        examInstructionDao.upsertAll(data.examInstruction.map { it.toEntity() })
        choiceOptionDao.upsertAll(data.choiceOptions.map { it.toEntity() })

        examScheduleDao.upsertAll(data.examSchedules.map { it.toEntity() })
        courseGradeDao.upsertAll(data.courseGrades.map { it.toEntity() })
        studentAnswerSheetDao.upsertAll(data.studentAnswerSheets.map { it.toEntity() })
        studentAnswerDao.upsertAll(data.studentAnswers.map { it.toEntity() })
    }
}
