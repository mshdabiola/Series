package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ClassAttendanceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassAttendanceDao {
    @Upsert
    suspend fun upsert(classAttendance: ClassAttendanceEntity): Long

    @Upsert
    suspend fun upsertAll(classAttendances: List<ClassAttendanceEntity>)

    @Delete
    suspend fun delete(classAttendance: ClassAttendanceEntity)

    @Query("DELETE FROM class_attendance")
    suspend fun deleteAllClassAttendances()

    @Query("SELECT * FROM class_attendance WHERE class_attendance_id = :attendanceId")
    fun getClassAttendanceById(attendanceId: Int): Flow<ClassAttendanceEntity?>

    @Query("SELECT * FROM class_attendance")
    fun getAllClassAttendances(): Flow<List<ClassAttendanceEntity>>

    @Query("SELECT * FROM class_attendance WHERE student_id_fk = :studentId")
    fun getClassAttendancesByStudentId(studentId: Int): Flow<List<ClassAttendanceEntity>>

    @Query("SELECT * FROM class_attendance WHERE class_id_fk = :classId")
    fun getClassAttendancesByClassId(classId: Int): Flow<List<ClassAttendanceEntity>>

//    @Query("SELECT * FROM class_attendance WHERE student_id_fk = :studentId AND class_id_fk = :classId AND attendance_date = :attendanceDate")
//    fun getClassAttendanceByCompositeKey(studentId: Int, classId: Int, attendanceDate: LocalDate): Flow<ClassAttendanceEntity?>

//    @Query("SELECT * FROM class_attendance WHERE attendance_date = :attendanceDate")
//    fun getClassAttendancesByDate(attendanceDate: LocalDate): Flow<List<ClassAttendanceEntity>>
}
