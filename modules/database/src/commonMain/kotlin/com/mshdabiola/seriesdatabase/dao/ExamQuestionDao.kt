package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExamQuestionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamQuestionDao {

    @Upsert
    suspend fun upsert(examQuestion: ExamQuestionEntity): Long

    @Upsert
    suspend fun upsertAll(examQuestions: List<ExamQuestionEntity>)

    @Delete
    suspend fun delete(examQuestion: ExamQuestionEntity)

    @Query("DELETE FROM exam_questions")
    suspend fun deleteAllExamQuestions()

    @Query("SELECT * FROM exam_questions WHERE question_id = :questionId")
    fun getExamQuestionById(questionId: Int): Flow<ExamQuestionEntity?>

    @Query("SELECT * FROM exam_questions")
    fun getAllExamQuestions(): Flow<List<ExamQuestionEntity>>

    @Query("SELECT * FROM exam_questions WHERE exam_paper_id_fk = :paperId")
    fun getExamQuestionsByPaperId(paperId: Int): Flow<List<ExamQuestionEntity>>

    @Query("SELECT * FROM exam_questions WHERE lesson_topic_id_fk = :topicId")
    fun getExamQuestionsByTopicId(topicId: Int): Flow<List<ExamQuestionEntity>>
}
