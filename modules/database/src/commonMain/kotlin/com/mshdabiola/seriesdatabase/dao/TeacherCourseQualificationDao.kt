package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.TeacherCourseQualificationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TeacherCourseQualificationDao {
    @Query("SELECT * FROM teacher_course_qualifications")
    fun getAllTeacherCourseQualifications(): Flow<List<TeacherCourseQualificationEntity>>

    @Query("SELECT * FROM teacher_course_qualifications WHERE teacherStaffId_fk = :teacherStaffId AND courseId_fk = :courseId")
    suspend fun getTeacherCourseQualificationByKey(teacherStaffId: Int, courseId: Int): TeacherCourseQualificationEntity?

    @Upsert
    suspend fun upsertTeacherCourseQualification(qualification: TeacherCourseQualificationEntity)

    @Upsert
    suspend fun upsertAllTeacherCourseQualifications(qualifications: List<TeacherCourseQualificationEntity>)

    @Delete
    suspend fun deleteTeacherCourseQualification(qualification: TeacherCourseQualificationEntity)

    @Query("DELETE FROM teacher_course_qualifications WHERE teacherStaffId_fk = :teacherStaffId AND courseId_fk = :courseId")
    suspend fun deleteTeacherCourseQualificationByKey(teacherStaffId: Int, courseId: Int)

    @Query("SELECT * FROM teacher_course_qualifications WHERE teacherStaffId_fk = :teacherStaffId")
    fun getTeacherCourseQualificationsForStaff(teacherStaffId: Int): Flow<List<TeacherCourseQualificationEntity>>

    @Query("SELECT * FROM teacher_course_qualifications WHERE courseId_fk = :courseId")
    fun getTeacherCourseQualificationsForCourse(courseId: Int): Flow<List<TeacherCourseQualificationEntity>>
}
