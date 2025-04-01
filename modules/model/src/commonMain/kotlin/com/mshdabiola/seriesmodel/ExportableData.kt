package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ExportableData(
    val schools: List<School> = emptyList(),
    val academicStaff: List<AcademicStaff> = emptyList(),
    val gradeLevels: List<GradeLevel> = emptyList(),
    val classes: List<ClassS> = emptyList(),
    val students: List<Student> = emptyList(),
    val courses: List<Course> = emptyList(),
    val lessonTopics: List<LessonTopic> = emptyList(),
    val learningMaterials: List<LearningMaterial> = emptyList(),
    val learningObjectives: List<LearningObjective> = emptyList(),
    val examPapers: List<ExamPaper> = emptyList(),
    val examSchedules: List<ExamSchedule> = emptyList(),
    val examInstruction: List<ExamInstruction> = emptyList(),
    val examQuestions: List<ExamQuestion> = emptyList(),
    val choiceOptions: List<ChoiceOption> = emptyList(),
    val studentAnswerSheets: List<StudentAnswerSheet> = emptyList(),
    val studentAnswers: List<StudentAnswer> = emptyList(),
    val courseGrades: List<CourseGrade> = emptyList(),
    val classAttendances: List<ClassAttendance> = emptyList(),
    val examAttendances: List<ExamAttendance> = emptyList(),
    val teacherCourseQualifications: List<TeacherCourseQualification>, // = emptyList()
)
