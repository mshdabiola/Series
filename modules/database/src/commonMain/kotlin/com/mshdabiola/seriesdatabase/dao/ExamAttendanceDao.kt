package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExamAttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamAttendanceDao {
    @Upsert
    suspend fun upsert(examAttendance: ExamAttendanceEntity): Long

    @Upsert
    suspend fun upsertAll(examAttendances: List<ExamAttendanceEntity>)

    @Delete
    suspend fun delete(examAttendance: ExamAttendanceEntity)

    @Query("DELETE FROM exam_attendance")
    suspend fun deleteAllExamAttendances()

    @Query("SELECT * FROM exam_attendance WHERE exam_attendance_id = :attendanceId")
    fun getExamAttendanceById(attendanceId: Int): Flow<ExamAttendanceEntity?>

    @Query("SELECT * FROM exam_attendance")
    fun getAllExamAttendances(): Flow<List<ExamAttendanceEntity>>

    @Query("SELECT * FROM exam_attendance WHERE student_id_fk = :studentId")
    fun getExamAttendancesByStudentId(studentId: Int): Flow<List<ExamAttendanceEntity>>

    @Query("SELECT * FROM exam_attendance WHERE exam_schedule_id_fk = :scheduleId")
    fun getExamAttendancesByScheduleId(scheduleId: Int): Flow<List<ExamAttendanceEntity>>

    @Query("SELECT * FROM exam_attendance WHERE student_id_fk = :studentId AND exam_schedule_id_fk = :scheduleId")
    fun getExamAttendanceByCompositeKey(studentId: Int, scheduleId: Int): Flow<ExamAttendanceEntity?>
}
