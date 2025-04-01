package com.mshdabiola.seriestesting

import com.mshdabiola.seriesmodel.AcademicStaff
import com.mshdabiola.seriesmodel.AttendanceStatus
import com.mshdabiola.seriesmodel.ChoiceOption
import com.mshdabiola.seriesmodel.ClassAttendance
import com.mshdabiola.seriesmodel.ClassS
import com.mshdabiola.seriesmodel.Content
import com.mshdabiola.seriesmodel.Course
import com.mshdabiola.seriesmodel.CourseGrade
import com.mshdabiola.seriesmodel.ExamAttendance
import com.mshdabiola.seriesmodel.ExamInstruction
import com.mshdabiola.seriesmodel.ExamPaper
import com.mshdabiola.seriesmodel.ExamQuestion
import com.mshdabiola.seriesmodel.ExamSchedule
import com.mshdabiola.seriesmodel.ExportableData
import com.mshdabiola.seriesmodel.GradeLevel
import com.mshdabiola.seriesmodel.LearningMaterial
import com.mshdabiola.seriesmodel.LearningObjective
import com.mshdabiola.seriesmodel.LessonTopic
import com.mshdabiola.seriesmodel.MaterialType
import com.mshdabiola.seriesmodel.QuestionType
import com.mshdabiola.seriesmodel.School
import com.mshdabiola.seriesmodel.StaffType
import com.mshdabiola.seriesmodel.Student
import com.mshdabiola.seriesmodel.StudentAnswer
import com.mshdabiola.seriesmodel.StudentAnswerSheet
import com.mshdabiola.seriesmodel.TeacherCourseQualification
import kotlinx.datetime.Clock.System
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime
import kotlin.random.Random

val examInstructions = listOf(
    ExamInstruction(
        1,
        1,
        "General Instructions",
        listOf(
            Content(content = "Read each question carefully."),
            Content(content = "Time limit: 60 minutes."),
        ),
    ),
    ExamInstruction(
        2,
        2,
        "Multiple Choice Section",
        listOf(
            Content(content = "Choose the best answer for each question."),
            Content(content = "Mark your answers on the answer sheet."),
        ),
    ),
    ExamInstruction(
        3,
        3,
        "Coding Instructions",
        listOf(
            Content(content = "Write your code in the provided space."),
            Content(content = "Use proper syntax and indentation."),
        ),
    ),
    ExamInstruction(
        4,
        4,
        "Essay Instructions",
        listOf(
            Content(content = "Answer the following questions in essay format."),
            Content(content = "Provide clear and concise explanations."),
        ),
    ),
    ExamInstruction(
        5,
        5,
        "Math Exam Instructions",
        listOf(
            Content(content = "Show all your work for full credit."),
            Content(content = "Use a calculator if needed."),
        ),
    ),
    ExamInstruction(
        6,
        6,
        "Web Design Instructions",
        listOf(
            Content(content = "Create a responsive web page."),
            Content(content = "Use HTML, CSS, and JavaScript."),
        ),
    ),
    ExamInstruction(
        7,
        7,
        "Art Project Instructions",
        listOf(
            Content(content = "Use the provided materials to create your artwork."),
            Content(content = "Be creative and express yourself."),
        ),
    ),
    ExamInstruction(
        8,
        8,
        "Music Performance Instructions",
        listOf(
            Content(content = "Prepare a piece of music to perform."),
            Content(content = "Be ready to answer questions about your piece."),
        ),
    ),
    ExamInstruction(
        9,
        9,
        "Language Exam Instructions",
        listOf(
            Content(content = "Answer the questions in the target language."),
            Content(content = "Use proper grammar and vocabulary."),
        ),
    ),
    ExamInstruction(
        10,
        10,
        "Physical Fitness Instructions",
        listOf(
            Content(content = "Warm up before starting the exercises."),
            Content(content = "Follow the instructions for each exercise."),
        ),
    ),
)

fun getExportable(): ExportableData {
    return ExportableData(
        schools = generateSchoolModels(),
        academicStaff = generateAcademicStaffModels(),
        gradeLevels = generateGradeLevelModels(),
        classes = generateClassModels(),
        students = generateStudentModels(),
        courses = generateCourseModels(),
        lessonTopics = generateLessonTopicModels(),
        learningMaterials = generateLearningMaterialModels(),
        learningObjectives = generateLearningObjectiveModels(),
        examPapers = generateExamPaperModels(),
        examSchedules = generateExamScheduleModels(),
        examInstruction = examInstructions,
        examQuestions = generateExamQuestionModels(),
        choiceOptions = generateChoiceOptionModels(),
        studentAnswerSheets = generateStudentAnswerSheetModels(),
        studentAnswers = generateStudentAnswerModels(),
        courseGrades = generateCourseGradeModels(),
        classAttendances = generateClassAttendanceModels(),
        examAttendances = generateExamAttendanceModels(),
        teacherCourseQualifications = generateTeacherCourseQualificationModels(),
    )
}

// --- Example Data Generation Functions for ALL Entities (15 Examples Each) using Lists - Backslash Removed ---

fun generateSchoolModels(): List<School> {
    val schoolNames = listOf(
        "Greenwood Academy",
        "Northwood High",
        "Southview College",
        "Eastside School",
        "Westmount Grammar",
        "Central Institute",
        "Lakeside Prep",
        "Mountain View School",
        "Ocean Breeze Academy",
        "Forest Hill College",
        "Riverside High",
        "Sunnyside School",
        "Valley Crest Academy",
        "Hilltop Institute",
        "Parkview College",
    )
    val schoolAddresses = listOf(
        "10 Pine Street, Townsville",
        "22 Oak Avenue, Cityburg",
        "35 Maple Lane, Villageville",
        "48 Willow Road, Hamletville",
        "51 Birch Blvd, Countryside",
        "64 Cedar Crescent, Metropolis",
        "77 Elm Estates, Ruraltown",
        "89 Spruce Square, Suburbia",
        "92 Pinecrest Place, Outskirts",
        "15 Redwood Ridge, Farmland",
        "28 Oakwood Drive, Hillside",
        "33 Maple Court, Riverside",
        "47 Willow Way, Lakeside",
        "52 Birch Place, Mountainview",
        "68 Cedar Grove, Valleyview",
    )
    return schoolNames.mapIndexed { index, name ->
        School(
            schoolId = index + 1L,
            schoolName = name,
            schoolAddress = schoolAddresses[index],
            academicYear = "2024-2025",
            // updatedAt = LocalDateTime.now().minusDays(Random.nextInt(30))
        )
    }
}

fun generateAcademicStaffModels(): List<AcademicStaff> {
    val staffTypes = StaffType.values()
    val firstNames = listOf(
        "Ava",
        "Liam",
        "Olivia",
        "Noah",
        "Emma",
        "Jackson",
        "Sophia",
        "Aiden",
        "Isabella",
        "Lucas",
        "Mia",
        "Ethan",
        "Charlotte",
        "Oliver",
        "Amelia",
    )
    val lastNames = listOf(
        "Smith",
        "Johnson",
        "Williams",
        "Brown",
        "Jones",
        "Garcia",
        "Miller",
        "Davis",
        "Rodriguez",
        "Martinez",
        "Hernandez",
        "Lopez",
        "Gonzalez",
        "Perez",
        "Wilson",
    )
    val contactDomains = listOf(
        "@greenwood.edu",
        "@northwood.org",
        "@southview.school",
        "@eastside.net",
        "@westmount.college",
    )

    return firstNames.mapIndexed { index, firstName ->
        AcademicStaff(
            staffId = index + 1L,
            staffType = staffTypes[Random.nextInt(staffTypes.size)],
            name = "$firstName ${lastNames[index]}",
            contactDetails = "${firstName.lowercase()}.${lastNames[index].lowercase()}${contactDomains.random()}",
            imagePath = "",
            password = "password123",
            schoolId = 1,
            // updatedAt = LocalDateTime.now().minusHours(Random.nextInt(72))
        )
    }
}

fun generateGradeLevelModels(): List<GradeLevel> {
    val gradeNames = listOf(
        "Grade 9",
        "Grade 10",
        "Grade 11",
        "Grade 12",
        "Freshman",
        "Sophomore",
        "Junior",
        "Senior",
        "Level A",
        "Level B",
        "Level C",
        "Level D",
        "Year 1",
        "Year 2",
        "Year 3",
    )
    val levelNumbers = (9..23).toList() // Example level numbers extending beyond grades

    return gradeNames.mapIndexed { index, name ->
        GradeLevel(
            gradeLevelId = index + 1L,
            gradeName = name,
            levelNumber = levelNumbers.getOrNull(index)?.toLong()
                ?: (index + 1L), // Fallback level number if list is shorter
            // updatedAt = LocalDateTime.now().minusDays(Random.nextInt(15)),
            schoolId = 1,
        )
    }
}

fun generateClassModels(): List<ClassS> {
    val classNames =
        listOf("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O")

    return classNames.mapIndexed { index, name ->
        ClassS(
            classId = index + 1L,
            className = "${(9 + (index) / 5)}$name", // Example: 9A, 9B, 9C, 9D, 9E, 9F, 9G, 9H, 9I, 9J, 9K, 9L, 9M, 9N, 9O, ... (repeats grade level after every 5 classes in this example - you can adjust logic)
            gradeLevelId = (index / 5) + 1L, // Grade level from 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3,... (repeats every 5 classes)
            teacherStaffId = if (Random.nextInt(10) < 8) (index % 10) + 1L else null, // 80% chance of having a teacher from staff 1-10, 20% chance null
            // updatedAt = LocalDateTime.now().minusHours(Random.nextInt(48))
        )
    }
}

fun generateStudentModels(): List<Student> {
    val studentFirstNames = listOf(
        "Oliver",
        "Elijah",
        "William",
        "James",
        "Benjamin",
        "Lucas",
        "Henry",
        "Alexander",
        "Theodore",
        "Samuel",
        "Scarlett",
        "Grace",
        "Chloe",
        "Victoria",
        "Hazel",
    )
    val studentLastNames = listOf(
        "Clark",
        "Young",
        "Walker",
        "Hall",
        "Wright",
        "King",
        "Green",
        "Adams",
        "Baker",
        "Nelson",
        "Carter",
        "Mitchell",
        "Roberts",
        "Turner",
        "Phillips",
    )

    return studentFirstNames.mapIndexed { index, firstName ->
        Student(
            studentId = index + 1L,
            name = "$firstName ${studentLastNames[index]}",
            dateOfBirth = System.now().toLocalDateTime(TimeZone.UTC).date, // Age 14-16 approx
            admissionNumber = "STU${String.format("%03d", index + 1)}",
            classId = (index / 3) + 1L, // Class from 1, 1, 1, 2, 2, 2, 3, 3, 3,... (repeats every 3 students)
//            updatedAt = LocalDateTime.now().minusDays(Random.nextInt(20))
        )
    }
}

fun generateCourseModels(): List<Course> {
    val courseNamesAndCodes = listOf(
        "Mathematics" to "MATH",
        "Science" to "SCI",
        "History" to "HIST",
        "English" to "ENG",
        "Art" to "ART",
        "Music" to "MUS",
        "Computer Science" to "CS",
        "Physics" to "PHYS",
        "Chemistry" to "CHEM",
        "Biology" to "BIO",
        "Geography" to "GEOG",
        "Economics" to "ECON",
        "Civics" to "CIV",
        "Psychology" to "PSYCH",
        "Sociology" to "SOC",
    )

    return courseNamesAndCodes.mapIndexed { index, pair ->
        val (courseName, courseCodePrefix) = pair
        Course(
            courseId = index + 101L, // Course IDs starting from 101
            courseName = courseName,
            courseCode = "${courseCodePrefix}${
                Random.nextInt(
                    9,
                    12,
                )
            }", // e.g., MATH9, MATH10, etc. grade level implied in code
            gradeLevelId = (index / 5) + 1L, // Grade level from 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3,... (repeats every 5 courses)
            // updatedAt = LocalDateTime.now().minusWeeks(Random.nextInt(4))
        )
    }
}

fun generateLessonTopicModels(): List<LessonTopic> {
    val topicPrefixes = listOf(
        "Intro to",
        "Fundamentals of",
        "Advanced",
        "Basics of",
        "Principles of",
        "Exploring",
        "Understanding",
        "Mastering",
        "Delving into",
        "Survey of",
        "A Study of",
        "The World of",
        "Beginner's Guide to",
        "Intermediate",
        "Expert Level",
    )
    val topicSubjects = listOf(
        "Algebra",
        "Physics",
        "Literature",
        "History",
        "Web Dev",
        "Calculus",
        "Thermodynamics",
        "Poetry",
        "Ancient Rome",
        "CSS",
        "Geometry",
        "Optics",
        "Drama",
        "Modern History",
        "JavaScript",
        "Kotlin",
        "React",
        "Databases",
        "Machine Learning",
        "Networking",
    )

    return topicPrefixes.mapIndexed { index, prefix ->
        LessonTopic(
            topicId = index + 1L,
            topicName = "$prefix ${topicSubjects[index % topicSubjects.size]}", // Cycle through subjects as prefixes repeat
            courseId = (index / 2) + 101L, // Course IDs from 101, 101, 102, 102, 103, 103,... (repeats every 2 topics)
            // updatedAt = LocalDateTime.now().minusDays(Random.nextInt(90))
        )
    }
}

fun generateLearningMaterialModels(): List<LearningMaterial> {
    val materialTypes = MaterialType.entries.toTypedArray()
    val materialTitles = listOf(
        "Chapter",
        "Notes",
        "Video",
        "Slides",
        "Worksheet",
        "Examples",
        "Map",
        "Lecture",
        "Cheat Sheet",
        "Tutorial",
        "Guide",
        "Document",
        "Article",
        "Presentation",
        "Exercise",
    )
    val materialSubjects = listOf(
        "Algebra",
        "Physics",
        "Literature",
        "History",
        "Web Dev",
        "Calculus",
        "Thermodynamics",
        "Poetry",
        "Ancient Rome",
        "CSS",
        "Geometry",
        "Optics",
        "Drama",
        "Modern History",
        "JavaScript",
    )

    return materialTitles.mapIndexed { index, title ->
        LearningMaterial(
            learningMaterialId = index + 1L,
            title = "${materialSubjects[index % materialSubjects.size]} $title", // Cycle through subjects as titles repeat
            description = "Description for Material ${index + 1}",
            materialType = materialTypes[index % materialTypes.size], // Cycle through material types
            filePath = if (Random.nextBoolean()) "files/material${index + 1}.pdf" else null, // Randomly assign file path or null - Backslash removed
            url = if (Random.nextBoolean()) "https://example.com/material${index + 1}" else null, // Randomly assign URL or null
            lessonTopicId = (index / 2) + 1L, // Lesson Topic from 1, 1, 2, 2, 3, 3,... (repeats every 2 materials)
            // updatedAt = LocalDateTime.now().minusHours(Random.nextInt(200))
        )
    }
}

fun generateLearningObjectiveModels(): List<LearningObjective> {
    val objectivePrefixes = listOf(
        "Understand",
        "Explain",
        "Analyze",
        "Describe",
        "Identify",
        "Apply",
        "Evaluate",
        "Compare",
        "Contrast",
        "Define",
        "Demonstrate",
        "Interpret",
        "Formulate",
        "Construct",
        "Summarize",
    )
    val objectiveTopics = listOf(
        "algebraic concepts",
        "Newton's laws",
        "Shakespearean sonnets",
        "ancient civilizations",
        "HTML basics",
        "calculus derivatives",
        "thermodynamic principles",
        "modern poetry themes",
        "Roman history",
        "CSS styling",
        "geometric theorems",
        "optical phenomena",
        "dramatic structure",
        "20th-century history",
        "JavaScript functions",
    ).map { listOf(Content(content = it)) }

    return objectivePrefixes.mapIndexed { index, prefix ->
        LearningObjective(
            learningObjectiveId = index + 1L,
            objectiveText = objectiveTopics[index], // Index directly to match prefixes and topics 1-to-1 for 15 examples
            lessonTopicId = (index / 1) + 1L, // Lesson Topic from 1, 2, 3, 4, 5,... (each objective different topic)
            // updatedAt = LocalDateTime.now().minusMinutes(Random.nextInt(1440)) // Up to 24 hours ago
        )
    }
}

fun generateExamPaperModels(): List<ExamPaper> {
    val paperTitles = listOf(
        "Midterm Exam",
        "Final Exam",
        "Quiz 1",
        "Quiz 2",
        "Test",
        "Unit Exam",
        "Chapter Test",
        "Practice Exam",
        "Assessment 1",
        "Assessment 2",
        "Exam Paper A",
        "Exam Paper B",
        "Monthly Test",
        "Weekly Quiz",
        "Diagnostic Test",
    )
    val courseIds = generateCourseModels().map { it.courseId } // Reuse generated course IDs
    val staffIds = generateAcademicStaffModels().map { it.staffId } // Reuse generated staff IDs

    return paperTitles.mapIndexed { index, title ->
        ExamPaper(
            examPaperId = index + 1L,
            paperTitle = "${generateCourseModels()[(index) % generateCourseModels().size].courseName} $title", // Cycle through course names as titles repeat
            courseId = courseIds[index % courseIds.size], // Cycle through course IDs
            creatorStaffId = staffIds[index % staffIds.size], // Cycle through staff IDs
            creationDate = System.now().toLocalDateTime(TimeZone.currentSystemDefault()),
            examScheduleId = null,
            year = 2019, // Exam Schedule can be added later
//            updatedAt = LocalDateTime.now().minusDays(Random.nextInt(60))
        )
    }
}

fun generateExamScheduleModels(): List<ExamSchedule> {
    val examNames = listOf(
        "Midterm",
        "Final",
        "Quiz",
        "Test",
        "Unit Exam",
        "Chapter Exam",
        "Practice Test",
        "Assessment",
        "Exam Session 1",
        "Exam Session 2",
        "Regular Exam",
        "Makeup Exam",
        "Class Test",
        "Section Quiz",
        "Annual Exam",
    )
    val classIds = generateClassModels().map { it.classId } // Reuse generated class IDs
    val examPaperIds =
        generateExamPaperModels().map { it.examPaperId } // Reuse generated exam paper IDs

    return examNames.mapIndexed { index, name ->
        ExamSchedule(
            examScheduleId = index + 1L,
            examName = "${generateCourseModels()[(index) % generateCourseModels().size].courseName} $name", // Cycle through course names as exam names repeat
            examDate = System.now().toLocalDateTime(TimeZone.UTC).date, // Exam in next 90 days
            startTime = System.now().toLocalDateTime(TimeZone.UTC).time, // 8 AM to 5 PM start times
            endTime = System.now()
                .toLocalDateTime(TimeZone.UTC).time, // End time is always after start time
            classId = classIds[index % classIds.size], // Cycle through class IDs
            examPaperId = examPaperIds[index % examPaperIds.size], // Cycle through exam paper IDs
            // updatedAt = LocalDateTime.now().minusHours(Random.nextInt(168)) // Up to 7 days ago
        )
    }
}

fun generateExamQuestionModels(): List<ExamQuestion> {
    val questionTypes =
        QuestionType.entries // listOf("Multiple Choice", "Short Answer", "Essay", "Coding", "True/False")
    val questionPrefixes = listOf(
        "Solve:",
        "Explain:",
        "Analyze:",
        "Describe:",
        "Identify:",
        "Write:",
        "Compare:",
        "Contrast:",
        "Define:",
        "Evaluate:",
    )
    val questionTopics = listOf(
        "linear equation",
        "Newton's laws",
        "metaphor in poetry",
        "Roman Empire features",
        "HTML structure",
        "calculus derivatives",
        "thermodynamics",
        "modern poetry",
        "Renaissance art",
        "CSS",
        "geometric shapes",
        "optical principles",
        "dramatic elements",
        "20th-century events",
        "JavaScript functions",
    ).toContents()

    return questionPrefixes.mapIndexed { index, prefix ->
        ExamQuestion(
            questionId = index + 1L,
            examPaperId = (index / 2) + 1L, // Exam Paper from 1, 1, 2, 2, 3, 3,... (repeats every 2 questions)
            questionText = questionTopics[index],
            questionType = questionTypes[index % questionTypes.size], // Cycle through question types
            marks = (index % 5) + 5L, // Marks from 5, 6, 7, 8, 9, 5, 6...
            lessonTopicId = (index / 1) + 1L,
            number = (index / 1) + 1L,
            instructionId = 2,
            answer = listOf(Content(content = "Answer ${index + 1}")), // Lesson Topic from 1, 2, 3, 4, 5,... (each question different topic)
            //  updatedAt = LocalDateTime.now().minusMinutes(Random.nextInt(1440 * 7)) // Up to 7 days ago
        )
    }
}

fun generateChoiceOptionModels(): List<ChoiceOption> {
    val optionLetters = listOf("A", "B", "C", "D", "E")
    val optionTexts = listOf(
        "Option Text 1",
        "Option Text 2",
        "Option Text 3",
        "Option Text 4",
        "Option Text 5",
        "Option Text 6",
        "Option Text 7",
        "Option Text 8",
        "Option Text 9",
        "Option Text 10",
        "Option Text 11",
        "Option Text 12",
        "Option Text 13",
        "Option Text 14",
        "Option Text 15",
    ).toContents()

    return optionLetters.mapIndexed { index, letter ->
        ChoiceOption(
            optionId = index + 1L,
            examQuestionId = (index / 4) + 1L, // Exam Question from 1, 1, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3,... (repeats every 4 options - assuming 4 options per question on average)
            optionText = optionTexts[index],
            isCorrect = Random.nextBoolean(),
//            updatedAt = LocalDateTime.now().minusHours(Random.nextInt(24 * 30)) // Up to 30 days ago
        )
    }
}

fun generateStudentAnswerSheetModels(): List<StudentAnswerSheet> {
    val studentIds = generateStudentModels().map { it.studentId }
    val examScheduleIds = generateExamScheduleModels().map { it.examScheduleId }

    return (1..15).map { index ->
        StudentAnswerSheet(
            answerSheetId = index + 1L,
            studentId = studentIds[index % studentIds.size], // Cycle through student IDs
            examScheduleId = examScheduleIds[index % examScheduleIds.size], // Cycle through exam schedule IDs
            submissionDate = System.now().toLocalDateTime(TimeZone.UTC),
            // updatedAt = LocalDateTime.now().minusDays(Random.nextInt(15))
        )
    }
}

fun generateStudentAnswerModels(): List<StudentAnswer> {
    val answerSheetIds = generateStudentAnswerSheetModels().map { it.answerSheetId }
    val examQuestionIds = generateExamQuestionModels().map { it.questionId }
    val choiceOptionIds = generateChoiceOptionModels().map { it.optionId }

    return (1..15).map { index ->
        StudentAnswer(
            studentAnswerId = index + 1L,
            answerSheetId = answerSheetIds[index % answerSheetIds.size], // Cycle through answer sheet IDs
            examQuestionId = examQuestionIds[index % examQuestionIds.size], // Cycle through exam question IDs
            answerText = listOf(Content(content = "Text Answer ${index + 1}")), // 50% text answers
            choiceOptionId = if (Random.nextBoolean()) choiceOptionIds[index % choiceOptionIds.size] else null, // 50% choice options
            isCorrect = Random.nextBoolean(),
            marksObtained = if (Random.nextBoolean()) {
                Random.nextInt(10)
                    .toLong()
            } else {
                null
            }, // Marks sometimes null if not graded yet
            //  updatedAt = LocalDateTime.now().minusHours(Random.nextInt(24 * 7)) // Up to 7 days ago
        )
    }
}

fun generateCourseGradeModels(): List<CourseGrade> {
    val studentIds = generateStudentModels().map { it.studentId }
    val courseIds = generateCourseModels().map { it.courseId }
    val gradeValues = listOf("A", "B+", "B", "C+", "C", "D", "F", "A-", "B-", "C-")
    val gradingSystems = listOf("Letter Grade", "Percentage", "GPA", "Pass/Fail")

    return (1..15).map { index ->
        CourseGrade(
            studentId = studentIds[index % studentIds.size], // Cycle through student IDs
            courseId = courseIds[index % courseIds.size], // Cycle through course IDs
            academicYear = "2024-2025",
            gradeValue = gradeValues[index % gradeValues.size], // Cycle through grade values
            gradingSystem = gradingSystems[index % gradingSystems.size], // Cycle through grading systems
//            updatedAt = LocalDateTime.now().minusMonths(Random.nextInt(3))
        )
    }
}

fun generateClassAttendanceModels(): List<ClassAttendance> {
    val studentIds = generateStudentModels().map { it.studentId }
    val classIds = generateClassModels().map { it.classId }
    val attendanceStatuses = AttendanceStatus.entries.toTypedArray()
    val reasons = listOf(
        null,
        "Sick",
        "Doctor Appointment",
        "Family Emergency",
        "Late - Traffic",
        "Excused Absence",
        "Field Trip",
    ) // Include null for present/no reason

    return (1..15).map { index ->
        ClassAttendance(
            classAttendanceId = index + 1L,
            studentId = studentIds[index % studentIds.size], // Cycle through student IDs
            classId = classIds[index % classIds.size], // Cycle through class IDs
            attendanceDate = System.now()
                .toLocalDateTime(TimeZone.UTC).date, // Attendance within last 30 days
            attendanceTime = System.now().toLocalDateTime(TimeZone.UTC).time, // 8 AM to 6 PM times
            attendanceStatus = attendanceStatuses[index % attendanceStatuses.size], // Cycle through statuses
            reason = reasons.random(), // Random reason, can be null
            // updatedAt = LocalDateTime.now().minusDays(Random.nextInt(7))
        )
    }
}

fun generateExamAttendanceModels(): List<ExamAttendance> {
    val studentIds = generateStudentModels().map { it.studentId }
    val examScheduleIds = generateExamScheduleModels().map { it.examScheduleId }
    val attendanceStatuses = AttendanceStatus.values()
    val reasons = listOf(
        null,
        "Sick",
        "Medical leave",
        "Exam conflict",
        "Personal reasons",
        "Late arrival",
        "Missed exam - rescheduled",
    ) // Include null for present/no reason

    return (1..15).map { index ->
        ExamAttendance(
            examAttendanceId = index + 1L,
            studentId = studentIds[index % studentIds.size], // Cycle through student IDs
            examScheduleId = examScheduleIds[index % examScheduleIds.size], // Cycle through exam schedule IDs
            attendanceStatus = attendanceStatuses[index % attendanceStatuses.size], // Cycle through attendance statuses
            reason = reasons.random(), // Random reason, can be null
//            updatedAt = LocalDateTime.now().minusDays(Random.nextInt(14))
        )
    }
}

fun generateTeacherCourseQualificationModels(): List<TeacherCourseQualification> {
    val staffIds = generateAcademicStaffModels().map { it.staffId }
    val courseIds = generateCourseModels().map { it.courseId }
    val notesList = listOf(
        "Certified and experienced",
        "Highly qualified",
        "Specialist in this subject",
        "Proficient teacher",
        "Expert knowledge",
        "Dedicated educator",
        "Passionate about teaching",
        "Excellent communicator",
        "Years of experience",
        "Advanced degree",
        "Continuously learning",
        "Innovative teaching methods",
        "Student-focused",
        "Engaging and effective",
        "Master teacher",
    )

    return (1..15).map { index ->
        TeacherCourseQualification(
            teacherStaffId = staffIds[index % staffIds.size], // Cycle through staff IDs
            courseId = courseIds[index % courseIds.size], // Cycle through course IDs
            qualificationDate = generateFutureDateTimeWithRandomness().date, // Qualification in last 5 years
            notes = notesList[index % notesList.size], // Cycle through notes
//            updatedAt = LocalDateTime.now()
//                .minusMonths(Random.nextInt(24)) // Updated within last 2 years
        )
    }
}

fun generateFutureDateTimeWithRandomness(
    minDaysToAdd: Int = 0,
    maxDaysToAdd: Int = 365,
    minHoursToAdd: Int = 0,
    maxHoursToAdd: Int = 23,
    minMinutesToAdd: Int = 0,
    maxMinutesToAdd: Int = 59,
    minSecondsToAdd: Int = 0,
    maxSecondsToAdd: Int = 59,
    timeZone: TimeZone = TimeZone.currentSystemDefault(),
): LocalDateTime {
    // Get the current instant
    val now: Instant = System.now()

    // Generate random components
    val randomDaysToAdd: Int =
        Random.nextInt(from = minDaysToAdd, until = maxDaysToAdd + 1) // +1 to make it inclusive
    val randomHoursToAdd: Int = Random.nextInt(from = minHoursToAdd, until = maxHoursToAdd + 1)
    val randomMinutesToAdd: Int =
        Random.nextInt(from = minMinutesToAdd, until = maxMinutesToAdd + 1)
    val randomSecondsToAdd: Int =
        Random.nextInt(from = minSecondsToAdd, until = maxSecondsToAdd + 1)

    // Calculate the future instant
    val futureInstant: Instant = now
        .plus(randomDaysToAdd, DateTimeUnit.DAY, timeZone)
        .plus(randomHoursToAdd, DateTimeUnit.HOUR, timeZone)
        .plus(randomMinutesToAdd, DateTimeUnit.MINUTE, timeZone)
        .plus(randomSecondsToAdd, DateTimeUnit.SECOND, timeZone)

    // Convert to LocalDateTime
    return futureInstant.toLocalDateTime(timeZone)
}

fun List<String>.toContents() = this.map { listOf(Content(content = it)) }
