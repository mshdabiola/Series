/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase

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
import com.mshdabiola.seriesmodel.AcademicStaff
import com.mshdabiola.seriesmodel.ChoiceOption
import com.mshdabiola.seriesmodel.ClassAttendance
import com.mshdabiola.seriesmodel.ClassS
import com.mshdabiola.seriesmodel.Course
import com.mshdabiola.seriesmodel.CourseGrade
import com.mshdabiola.seriesmodel.ExamAttendance
import com.mshdabiola.seriesmodel.ExamPaper
import com.mshdabiola.seriesmodel.ExamQuestion
import com.mshdabiola.seriesmodel.ExamSchedule
import com.mshdabiola.seriesmodel.GradeLevel
import com.mshdabiola.seriesmodel.LearningMaterial
import com.mshdabiola.seriesmodel.LearningObjective
import com.mshdabiola.seriesmodel.LessonTopic
import com.mshdabiola.seriesmodel.School
import com.mshdabiola.seriesmodel.Student
import com.mshdabiola.seriesmodel.StudentAnswer
import com.mshdabiola.seriesmodel.StudentAnswerSheet

fun Long.checkId() = if (this < 0) null else this

// School Converters
fun SchoolEntity.toDomain(): School =
    School(
        schoolId = this.schoolId,
        schoolName = this.schoolName,
        schoolAddress = this.schoolAddress,
        academicYear = this.academicYear,
    )

fun School.toEntity(): SchoolEntity =
    SchoolEntity(
        schoolId = this.schoolId,
        schoolName = this.schoolName,
        schoolAddress = this.schoolAddress,
        academicYear = this.academicYear,
    )

// AcademicStaff Converters
fun AcademicStaffEntity.toDomain(): AcademicStaff =
    AcademicStaff(
        staffId = this.staffId,
        staffType = this.staffType,
        name = this.name,
        contactDetails = this.contactDetails,
        password = this.password,
        imagePath = this.imagePath,
    )

fun AcademicStaff.toEntity(): AcademicStaffEntity =
    AcademicStaffEntity(
        staffId = this.staffId,
        staffType = this.staffType,
        name = this.name,
        contactDetails = this.contactDetails,
        password = this.password,
        imagePath = this.imagePath,
    )

// GradeLevel Converters
fun GradeLevelEntity.toDomain(): GradeLevel =
    GradeLevel(
        gradeLevelId = this.gradeLevelId,
        gradeName = this.gradeName,
        levelNumber = this.levelNumber,
    )

fun GradeLevel.toEntity(): GradeLevelEntity =
    GradeLevelEntity(
        gradeLevelId = this.gradeLevelId,
        gradeName = this.gradeName,
        levelNumber = this.levelNumber,
    )

// Class Converters
fun ClassEntity.toDomain(): ClassS =
    ClassS(
        classId = this.classId,
        className = this.className,
        gradeLevelId = this.gradeLevelId,
        teacherStaffId = this.teacherStaffId,
    )

fun ClassS.toEntity(): ClassEntity =
    ClassEntity(
        classId = this.classId,
        className = this.className,
        gradeLevelId = this.gradeLevelId,
        teacherStaffId = this.teacherStaffId,
    )

// Student Converters
fun StudentEntity.toDomain(): Student =
    Student(
        studentId = this.studentId,
        name = this.name,
        dateOfBirth = this.dateOfBirth,
        admissionNumber = this.admissionNumber,
        classId = this.classId,
    )

fun Student.toEntity(): StudentEntity =
    StudentEntity(
        studentId = this.studentId,
        name = this.name,
        dateOfBirth = this.dateOfBirth,
        admissionNumber = this.admissionNumber,
        classId = this.classId,
    )

// Course Converters
fun CourseEntity.toDomain(): Course =
    Course(
        courseId = this.courseId,
        courseName = this.courseName,
        courseCode = this.courseCode,
        gradeLevelId = this.gradeLevelId,
    )

fun Course.toEntity(): CourseEntity =
    CourseEntity(
        courseId = this.courseId,
        courseName = this.courseName,
        courseCode = this.courseCode,
        gradeLevelId = this.gradeLevelId,
    )

// LessonTopic Converters
fun LessonTopicEntity.toDomain(): LessonTopic =
    LessonTopic(
        topicId = this.topicId,
        topicName = this.topicName,
        courseId = this.courseId,
    )

fun LessonTopic.toEntity(): LessonTopicEntity =
    LessonTopicEntity(
        topicId = this.topicId,
        topicName = this.topicName,
        courseId = this.courseId,
    )

// LearningMaterial Converters
fun LearningMaterialEntity.toDomain(): LearningMaterial =
    LearningMaterial(
        learningMaterialId = this.learningMaterialId,
        title = this.title,
        description = this.description,
        materialType = this.materialType,
        filePath = this.filePath,
        url = this.url,
        lessonTopicId = this.lessonTopicId,
    )

fun LearningMaterial.toEntity(): LearningMaterialEntity =
    LearningMaterialEntity(
        learningMaterialId = this.learningMaterialId,
        title = this.title,
        description = this.description,
        materialType = this.materialType,
        filePath = this.filePath,
        url = this.url,
        lessonTopicId = this.lessonTopicId,
    )

// LearningObjective Converters
fun LearningObjectiveEntity.toDomain(): LearningObjective =
    LearningObjective(
        learningObjectiveId = this.learningObjectiveId,
        objectiveText = this.objectiveText,
        lessonTopicId = this.lessonTopicId,
    )

fun LearningObjective.toEntity(): LearningObjectiveEntity =
    LearningObjectiveEntity(
        learningObjectiveId = this.learningObjectiveId,
        objectiveText = this.objectiveText,
        lessonTopicId = this.lessonTopicId,
    )

// ExamPaper Converters
fun ExamPaperEntity.toDomain(): ExamPaper =
    ExamPaper(
        examPaperId = this.examPaperId,
        paperTitle = this.paperTitle,
        courseId = this.courseId,
        creatorStaffId = this.creatorStaffId,
        creationDate = this.creationDate,
        examScheduleId = this.examScheduleId,
        year = this.year,
    )

fun ExamPaper.toEntity(): ExamPaperEntity =
    ExamPaperEntity(
        examPaperId = this.examPaperId,
        paperTitle = this.paperTitle,
        courseId = this.courseId,
        creatorStaffId = this.creatorStaffId,
        creationDate = this.creationDate,
        examScheduleId = this.examScheduleId,
        year = this.year,
    )

// ExamSchedule Converters
fun ExamScheduleEntity.toDomain(): ExamSchedule =
    ExamSchedule(
        examScheduleId = this.examScheduleId,
        examName = this.examName,
        examDate = this.examDate,
        startTime = this.startTime,
        endTime = this.endTime,
        classId = this.classId,
        examPaperId = this.examPaperId,
    )

fun ExamSchedule.toEntity(): ExamScheduleEntity =
    ExamScheduleEntity(
        examScheduleId = this.examScheduleId,
        examName = this.examName,
        examDate = this.examDate,
        startTime = this.startTime,
        endTime = this.endTime,
        classId = this.classId,
        examPaperId = this.examPaperId,
    )

// ExamQuestion Converters
fun ExamQuestionEntity.toDomain(): ExamQuestion =
    ExamQuestion(
        questionId = this.questionId,
        examPaperId = this.examPaperId,
        questionText = this.questionText,
        questionType = this.questionType,
        marks = this.marks,
        lessonTopicId = this.lessonTopicId,
        answer= this.answer,
        number = this.number,
        title = this.title,
        instructionId = this.instructionId,
    )

fun ExamQuestion.toEntity(): ExamQuestionEntity =
    ExamQuestionEntity(
        questionId = this.questionId,
        examPaperId = this.examPaperId,
        questionText = this.questionText,
        questionType = this.questionType,
        marks = this.marks,
        lessonTopicId = this.lessonTopicId,
        answer= this.answer,
        number = this.number,
        title = this.title,
        instructionId = this.instructionId,
    )

// ChoiceOption Converters
fun ChoiceOptionEntity.toDomain(): ChoiceOption =
    ChoiceOption(
        optionId = this.optionId,
        examQuestionId = this.examQuestionId,
        optionText = this.optionText,
        isCorrect = this.isCorrect,
        title = this.title,
        contents = this.contents,
    )

fun ChoiceOption.toEntity(): ChoiceOptionEntity =
    ChoiceOptionEntity(
        optionId = this.optionId,
        examQuestionId = this.examQuestionId,
        optionText = this.optionText,
        isCorrect = this.isCorrect,
        title = this.title,
        contents = this.contents,
    )

// StudentAnswerSheet Converters
fun StudentAnswerSheetEntity.toDomain(): StudentAnswerSheet =
    StudentAnswerSheet(
        answerSheetId = this.answerSheetId,
        studentId = this.studentId,
        examScheduleId = this.examScheduleId,
        submissionDate = this.submissionDate,
    )

fun StudentAnswerSheet.toEntity(): StudentAnswerSheetEntity =
    StudentAnswerSheetEntity(
        answerSheetId = this.answerSheetId,
        studentId = this.studentId,
        examScheduleId = this.examScheduleId,
        submissionDate = this.submissionDate,
    )

// StudentAnswer Converters
fun StudentAnswerEntity.toDomain(): StudentAnswer =
    StudentAnswer(
        studentAnswerId = this.studentAnswerId,
        answerSheetId = this.answerSheetId,
        examQuestionId = this.examQuestionId,
        answerText = this.answerText,
        choiceOptionId = this.choiceOptionId,
        isCorrect = this.isCorrect,
        marksObtained = this.marksObtained,
    )

fun StudentAnswer.toEntity(): StudentAnswerEntity =
    StudentAnswerEntity(
        studentAnswerId = this.studentAnswerId,
        answerSheetId = this.answerSheetId,
        examQuestionId = this.examQuestionId,
        answerText = this.answerText,
        choiceOptionId = this.choiceOptionId,
        isCorrect = this.isCorrect,
        marksObtained = this.marksObtained,
    )

// CourseGrade Converters
fun CourseGradeEntity.toDomain(): CourseGrade =
    CourseGrade(
        //  courseGradeId = 0, // Domain model doesn't have PK, or handle as needed if you do need it in domain
        studentId = this.studentId,
        courseId = this.courseId,
        academicYear = this.academicYear,
        gradeValue = this.gradeValue,
        gradingSystem = this.gradingSystem,
    )

fun CourseGrade.toEntity(): CourseGradeEntity =
    CourseGradeEntity(
        studentId = this.studentId,
        courseId = this.courseId,
        academicYear = this.academicYear,
        gradeValue = this.gradeValue,
        gradingSystem = this.gradingSystem,
    )

// ClassAttendance Converters
fun ClassAttendanceEntity.toDomain(): ClassAttendance =
    ClassAttendance(
        classAttendanceId = this.classAttendanceId,
        studentId = this.studentId,
        classId = this.classId,
        attendanceDate = this.attendanceDate,
        attendanceTime = this.attendanceTime,
        attendanceStatus = this.attendanceStatus,
        reason = this.reason,
    )

fun ClassAttendance.toEntity(): ClassAttendanceEntity =
    ClassAttendanceEntity(
        classAttendanceId = this.classAttendanceId,
        studentId = this.studentId,
        classId = this.classId,
        attendanceDate = this.attendanceDate,
        attendanceTime = this.attendanceTime,
        attendanceStatus = this.attendanceStatus,
        reason = this.reason,
    )

// ExamAttendance Converters
fun ExamAttendanceEntity.toDomain(): ExamAttendance =
    ExamAttendance(
        examAttendanceId = this.examAttendanceId,
        studentId = this.studentId,
        examScheduleId = this.examScheduleId,
        attendanceStatus = this.attendanceStatus,
        reason = this.reason,
    )

fun ExamAttendance.toEntity(): ExamAttendanceEntity =
    ExamAttendanceEntity(
        examAttendanceId = this.examAttendanceId,
        studentId = this.studentId,
        examScheduleId = this.examScheduleId,
        attendanceStatus = this.attendanceStatus,
        reason = this.reason,
    )
