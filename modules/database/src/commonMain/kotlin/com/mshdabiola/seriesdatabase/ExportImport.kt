package com.mshdabiola.seriesdatabase

import com.mshdabiola.seriesmodel.ExportableData
import com.mshdabiola.seriesmodel.Security
import com.mshdabiola.seriesmodel.unzipFile
import com.mshdabiola.seriesmodel.zipDirectory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ExportImport(
    private val database: SchoolDatabase,
) {
    suspend fun export(
        examsId: Set<Long>,
        outputStream: OutputStream,
        password: String,
    ) {
        withContext(Dispatchers.IO) {
            val tempFile = createTempFolder()
            val job = launch {
                // val db by inject<SeriesDatabase>(qualifier = qualifier("tem"), parameters = { parametersOf(dbPath.path) })

                val schoolsAsync = async {
                    database.schoolDao().getAllSchools().first()
                        .map { it.toDomain() } // Get all schools for simplicity for now, adapt filter if needed
                }
                val academicStaffAsync = async {
                    database.academicStaffDao().getAllAcademicStaff().first().map { it.toDomain() }
                }
                val gradeLevelsAsync = async {
                    database.gradeLevelDao().getAllGradeLevels().first().map { it.toDomain() }
                }
                val classesAsync = async {
                    database.classDao().getAllClasses().first().map { it.toDomain() }
                }
                val studentsAsync = async {
                    database.studentDao().getAllStudents().first().map { it.toDomain() }
                }
                val coursesAsync = async {
                    database.courseDao().getAllCourses().first().map { it.toDomain() }
                }
                val lessonTopicsAsync = async {
                    database.lessonTopicDao().getAllLessonTopics().first().map { it.toDomain() }
                }
                val learningMaterialsAsync = async {
                    database.learningMaterialDao().getAllLearningMaterials().first()
                        .map { it.toDomain() }
                }
                val learningObjectivesAsync = async {
                    database.learningObjectiveDao().getAllLearningObjectives().first()
                        .map { it.toDomain() }
                }
                val examPapersAsync = async {
                    database.examPaperDao().getAllExamPapers().first().map { it.toDomain() }
                }
                val examSchedulesAsync = async {
                    database.examScheduleDao().getAllExamSchedules().first().map { it.toDomain() }
                }
                val examQuestionsAsync = async {
                    database.examQuestionDao().getAllExamQuestions().first().map { it.toDomain() }
                }
                val choiceOptionsAsync = async {
                    database.choiceOptionDao().getAllChoiceOptions().first().map { it.toDomain() }
                }
                val studentAnswerSheetsAsync = async {
                    database.studentAnswerSheetDao().getAllStudentAnswerSheets().first()
                        .map { it.toDomain() }
                }
                val studentAnswersAsync = async {
                    database.studentAnswerDao().getAllStudentAnswers().first().map { it.toDomain() }
                }
                val courseGradesAsync = async {
                    database.courseGradeDao().getAllCourseGrades().first().map { it.toDomain() }
                }
                val classAttendancesAsync = async {
                    database.classAttendanceDao().getAllClassAttendances().first()
                        .map { it.toDomain() }
                }
                val examAttendancesAsync = async {
                    database.examAttendanceDao().getAllExamAttendances().first()
                        .map { it.toDomain() }
                }

                val teacherCourseQualificationsAsync = async {
                    database.teacherCourseQualificationDao().getAllTeacherCourseQualifications()
                        .first().map { it.toDomain() }
                }

                val data = ExportableData(
                    schools = schoolsAsync.await(),
                    academicStaff = academicStaffAsync.await(),
                    gradeLevels = gradeLevelsAsync.await(),
                    classes = classesAsync.await(),
                    students = studentsAsync.await(),
                    courses = coursesAsync.await(),
                    lessonTopics = lessonTopicsAsync.await(),
                    learningMaterials = learningMaterialsAsync.await(),
                    learningObjectives = learningObjectivesAsync.await(),
                    examPapers = examPapersAsync.await(),
                    examSchedules = examSchedulesAsync.await(),
                    examQuestions = examQuestionsAsync.await(),
                    choiceOptions = choiceOptionsAsync.await(),
                    studentAnswerSheets = studentAnswerSheetsAsync.await(),
                    studentAnswers = studentAnswersAsync.await(),
                    courseGrades = courseGradesAsync.await(),
                    classAttendances = classAttendancesAsync.await(),
                    examAttendances = examAttendancesAsync.await(),
                    teacherCourseQualifications = teacherCourseQualificationsAsync.await(),

                )

                val string = Security.encodeData(data)

                val output = File(tempFile, "data.json")
                output.createNewFile()

                output.writeText(string)
            }
            val job2 = launch {
                val imagePath = File(tempFile, "image")
                copyImage(imagePath, examsId)
            }

            job.join()
            job2.join()
            if (tempFile != null) {
                zipDirectory(tempFile, outputStream, password)
            }
        }
    }

    suspend fun import(inputStream: InputStream, key: String) {
        withContext(Dispatchers.IO) {
            val tempFile = createTempFolder()
            if (tempFile != null) {
                unzipFile(inputStream, tempFile.path, key)

                launch {
                    val temp = File(tempFile, "data.json").toPath()

                    val data = Security.decodeData(temp)

                    launch {
                        database.schoolDao().upsertAll(data.schools.map { it.toEntity() })
                    }.join()
                    launch {
                        database.academicStaffDao()
                            .upsertAll(data.academicStaff.map { it.toEntity() })
                    }.join()
                    launch {
                        database.gradeLevelDao().upsertAll(data.gradeLevels.map { it.toEntity() })
                    }.join()
                    launch {
                        database.classDao().upsertAll(data.classes.map { it.toEntity() })
                    }.join()
                    launch {
                        database.studentDao().upsertAll(data.students.map { it.toEntity() })
                    }.join()
                    launch {
                        database.courseDao().upsertAll(data.courses.map { it.toEntity() })
                    }.join()
                    launch {
                        database.lessonTopicDao().upsertAll(data.lessonTopics.map { it.toEntity() })
                    }.join()
                    launch {
                        database.learningMaterialDao()
                            .upsertAll(data.learningMaterials.map { it.toEntity() })
                    }.join()
                    launch {
                        database.learningObjectiveDao()
                            .upsertAll(data.learningObjectives.map { it.toEntity() })
                    }.join()
                    launch {
                        database.examScheduleDao()
                            .upsertAll(data.examSchedules.map { it.toEntity() }) // ExamSchedule before ExamPaper due to FK
                    }.join()
                    launch {
                        database.examPaperDao().upsertAll(data.examPapers.map { it.toEntity() })
                    }.join()
                    launch {
                        database.examQuestionDao()
                            .upsertAll(data.examQuestions.map { it.toEntity() })
                    }.join()
                    launch {
                        database.choiceOptionDao()
                            .upsertAll(data.choiceOptions.map { it.toEntity() })
                    }.join()
                    launch {
                        database.studentAnswerSheetDao()
                            .upsertAll(data.studentAnswerSheets.map { it.toEntity() })
                    }.join()
                    launch {
                        database.studentAnswerDao()
                            .upsertAll(data.studentAnswers.map { it.toEntity() })
                    }.join()
                    launch {
                        database.courseGradeDao().upsertAll(data.courseGrades.map { it.toEntity() })
                    }.join()
                    launch {
                        database.classAttendanceDao()
                            .upsertAll(data.classAttendances.map { it.toEntity() })
                    }.join()
                    launch {
                        database.examAttendanceDao()
                            .upsertAll(data.examAttendances.map { it.toEntity() })
                    }.join()
                    launch {
                        database.teacherCourseQualificationDao()
                            .upsertAllTeacherCourseQualifications(data.teacherCourseQualifications.map { it.toEntity() })
                    }.join()
                }
                launch {
                    val imagePath = File(tempFile, "image")
                    imagePath.listFiles()?.forEach {
                        it.copyRecursively(File(generalPath, "image/${it.name}"), true)
                    }
                }
            }
        }
    }

    private suspend fun copyImage(dir: File, examsId: Set<Long>) {
        withContext(Dispatchers.IO) {
            try {
                examsId.forEach {
                    val from = File(generalPath, "image/$it")
                    val to = File(dir.path, "$it")
                    // to.createParentDirectories()
                    if (from.exists()) {
                        from.copyRecursively(to, overwrite = true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun createTempFolder(): File? {
        val tempFolder = File.createTempFile("temp", "", null)
        if (tempFolder.delete() && tempFolder.mkdirs()) {
            return tempFolder
        }
        return null
    }
}
