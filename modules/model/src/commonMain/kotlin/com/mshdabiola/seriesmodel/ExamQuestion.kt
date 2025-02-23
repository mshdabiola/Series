/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ExamQuestion(
    val questionId: Long = -1, // PK, Auto-generate
    val examPaperId: Long, // FK to ExamPaper.examPaperId
    val questionText: List<Content>,
    val questionType: QuestionType, // e.g., "MCQ", "Short Answer", "Essay"
    val marks: Long,
    val lessonTopicId: Long? = null, // FK to LessonTopic.topicId - Optional
    val number: Long,

    val instructionId: Long?,

    val answer: List<Content>,
)
