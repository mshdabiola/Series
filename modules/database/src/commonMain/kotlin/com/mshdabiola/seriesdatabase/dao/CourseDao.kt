package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.CourseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CourseDao {
    @Upsert
    suspend fun upsert(course: CourseEntity)

    @Upsert
    suspend fun upsertAll(courses: List<CourseEntity>)

    @Delete()
    suspend fun delete(course: CourseEntity)

    @Query("DELETE FROM courses")
    suspend fun deleteAllCourses()

    @Query("SELECT * FROM courses WHERE course_id = :courseId")
    fun getCourseById(courseId: Int): Flow<CourseEntity?>

    @Query("SELECT * FROM courses")
    fun getAllCourses(): Flow<List<CourseEntity>>

    @Query("SELECT * FROM courses WHERE grade_level_id_fk = :gradeLevelId")
    fun getCoursesByGradeLevelId(gradeLevelId: Int): Flow<List<CourseEntity>>
}
