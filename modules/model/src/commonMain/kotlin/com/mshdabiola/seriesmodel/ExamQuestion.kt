/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class ExamQuestion(
    val questionId: Int = 0, // PK, Auto-generate
    val examPaperId: Int, // FK to ExamPaper.examPaperId
    val questionText: String,
    val questionType: String, // e.g., "MCQ", "Short Answer", "Essay"
    val marks: Int,
    val lessonTopicId: Int? = null, // FK to LessonTopic.topicId - Optional
    val number: Long,
    val title: String,

    val instructionId: Long?,

    val answer: String,
)
