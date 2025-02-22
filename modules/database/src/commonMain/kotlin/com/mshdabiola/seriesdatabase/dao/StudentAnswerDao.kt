package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.StudentAnswerEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentAnswerDao {
    @Upsert
    suspend fun upsert(answer: StudentAnswerEntity): Long

    @Upsert
    suspend fun upsertAll(answers: List<StudentAnswerEntity>)

    @Delete
    suspend fun delete(answer: StudentAnswerEntity)

    @Query("DELETE FROM student_answers")
    suspend fun deleteAllStudentAnswers()

    @Query("SELECT * FROM student_answers WHERE student_answer_id = :answerId")
    fun getStudentAnswerById(answerId: Int): Flow<StudentAnswerEntity?>

    @Query("SELECT * FROM student_answers")
    fun getAllStudentAnswers(): Flow<List<StudentAnswerEntity>>

    @Query("SELECT * FROM student_answers WHERE answer_sheet_id_fk = :answerSheetId")
    fun getStudentAnswersByAnswerSheetId(answerSheetId: Int): Flow<List<StudentAnswerEntity>>

    @Query("SELECT * FROM student_answers WHERE exam_question_id_fk = :questionId")
    fun getStudentAnswersByQuestionId(questionId: Int): Flow<List<StudentAnswerEntity>>
}
