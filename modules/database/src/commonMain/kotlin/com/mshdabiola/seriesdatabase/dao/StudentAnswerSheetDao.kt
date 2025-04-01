package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.StudentAnswerSheetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StudentAnswerSheetDao {
    @Upsert
    suspend fun upsertAll(answerSheets: List<StudentAnswerSheetEntity>): List<Long>

    @Upsert
    suspend fun upsert(answerSheet: StudentAnswerSheetEntity): Long

    @Delete
    suspend fun delete(answerSheet: StudentAnswerSheetEntity)

    @Query("DELETE FROM student_answer_sheets")
    suspend fun deleteAllStudentAnswerSheets()

    @Query("SELECT * FROM student_answer_sheets WHERE answer_sheet_id = :answerSheetId")
    fun getStudentAnswerSheetById(answerSheetId: Int): Flow<StudentAnswerSheetEntity?>

    @Query("SELECT * FROM student_answer_sheets")
    fun getAllStudentAnswerSheets(): Flow<List<StudentAnswerSheetEntity>>

    @Query("SELECT * FROM student_answer_sheets WHERE student_id_fk = :studentId")
    fun getStudentAnswerSheetsByStudentId(studentId: Int): Flow<List<StudentAnswerSheetEntity>>

    @Query("SELECT * FROM student_answer_sheets WHERE exam_schedule_id_fk = :scheduleId")
    fun getStudentAnswerSheetsByScheduleId(scheduleId: Int): Flow<List<StudentAnswerSheetEntity>>
}
