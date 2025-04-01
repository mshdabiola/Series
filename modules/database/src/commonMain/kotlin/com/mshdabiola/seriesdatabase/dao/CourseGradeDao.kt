package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.CourseGradeEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface CourseGradeDao {

    @Upsert
    suspend fun upsert(courseGrade: CourseGradeEntity)

    @Upsert
    suspend fun upsertAll(courseGrades: List<CourseGradeEntity>)

    @Delete
    suspend fun delete(courseGrade: CourseGradeEntity)

    @Query("DELETE FROM course_grades")
    suspend fun deleteAllCourseGrades()

    @Query("SELECT * FROM course_grades WHERE student_id_fk = :studentId AND course_id_fk = :courseId AND academic_year = :academicYear")
    fun getCourseGradeByCompositeKey(studentId: Int, courseId: Int, academicYear: String): Flow<CourseGradeEntity?>

    @Query("SELECT * FROM course_grades WHERE student_id_fk = :studentId AND academic_year = :academicYear")
    fun getCourseGradesForStudentInYear(studentId: Int, academicYear: String): Flow<List<CourseGradeEntity>>

    @Query("SELECT * FROM course_grades WHERE course_id_fk = :courseId AND academic_year = :academicYear")
    fun getCourseGradesForCourseInYear(courseId: Int, academicYear: String): Flow<List<CourseGradeEntity>>

    @Query("SELECT * FROM course_grades WHERE student_id_fk = :studentId")
    fun getCourseGradesByStudentId(studentId: Int): Flow<List<CourseGradeEntity>>

    @Query("SELECT * FROM course_grades WHERE course_id_fk = :courseId")
    fun getCourseGradesByCourseId(courseId: Int): Flow<List<CourseGradeEntity>>

    @Query("SELECT * FROM course_grades")
    fun getAllCourseGrades(): Flow<List<CourseGradeEntity>>
}
