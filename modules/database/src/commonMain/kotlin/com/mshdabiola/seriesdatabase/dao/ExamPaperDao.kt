package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExamPaperEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamPaperDao {
    @Upsert
    suspend fun upsert(examPaper: ExamPaperEntity)

    @Upsert
    suspend fun upsertAll(examPapers: List<ExamPaperEntity>)

    @Delete
    suspend fun delete(examPaper: ExamPaperEntity)

    @Query("DELETE FROM exam_papers")
    suspend fun deleteAllExamPapers()

    @Query("SELECT * FROM exam_papers WHERE exam_paper_id = :examPaperId")
    fun getExamPaperById(examPaperId: Int): Flow<ExamPaperEntity?>

    @Query("SELECT * FROM exam_papers")
    fun getAllExamPapers(): Flow<List<ExamPaperEntity>>

    @Query("SELECT * FROM exam_papers WHERE course_id_fk = :courseId")
    fun getExamPapersByCourseId(courseId: Int): Flow<List<ExamPaperEntity>>

    @Query("SELECT * FROM exam_papers WHERE creator_staff_id_fk = :staffId")
    fun getExamPapersByCreatorStaffId(staffId: Int): Flow<List<ExamPaperEntity>>

    @Query("SELECT * FROM exam_papers WHERE exam_schedule_id_fk = :scheduleId")
    fun getExamPapersByScheduleId(scheduleId: Int): Flow<List<ExamPaperEntity>>
}
