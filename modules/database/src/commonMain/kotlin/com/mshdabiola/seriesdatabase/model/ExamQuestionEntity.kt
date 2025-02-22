/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters
import com.mshdabiola.seriesmodel.QuestionType

@Entity(
    tableName = "exam_questions",
    foreignKeys = [
        ForeignKey(
            entity = ExamPaperEntity::class,
            parentColumns = ["exam_paper_id"],
            childColumns = ["exam_paper_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete questions if exam paper is deleted
        ),
        ForeignKey(
            entity = LessonTopicEntity::class,
            parentColumns = ["topic_id"],
            childColumns = ["lesson_topic_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Optional topic link - allow questions even if topic is deleted, set topic link to null
        ),
    ],
    indices = [
        Index(value = ["exam_paper_id_fk"]),
        Index(value = ["lesson_topic_id_fk"]),
    ],
)
@TypeConverters(Converters::class)
data class ExamQuestionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "question_id")
    val questionId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "exam_paper_id_fk") // Foreign Key column name
    val examPaperId: Long, // FK to ExamPaper.examPaperId

    @ColumnInfo(name = "question_type")
    val questionType: QuestionType, // e.g., "MCQ", "Short Answer", "Essay"

    @ColumnInfo(name = "marks")
    val marks: Long,

    @ColumnInfo(name = "lesson_topic_id_fk", defaultValue = "NULL") // Foreign Key column name, optional
    val lessonTopicId: Long? = null, // FK to LessonTopic.topicId - Optional

    val number: Long,
    val title: String,

    @ColumnInfo(name = "question_text")
    val questionText: String,

    val instructionId: Long?,

    val answer: String,
)
