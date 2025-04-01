package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExamScheduleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamScheduleDao {
    @Upsert
    suspend fun upsertAll(examSchedules: List<ExamScheduleEntity>)

    @Upsert
    suspend fun upsert(examSchedule: ExamScheduleEntity)

    @Delete
    suspend fun delete(examSchedule: ExamScheduleEntity)

    @Query("DELETE FROM exam_schedules")
    suspend fun deleteAllExamSchedules()

    @Query("SELECT * FROM exam_schedules WHERE exam_schedule_id = :examScheduleId")
    fun getExamScheduleById(examScheduleId: Int): Flow<ExamScheduleEntity?>

    @Query("SELECT * FROM exam_schedules")
    fun getAllExamSchedules(): Flow<List<ExamScheduleEntity>>

    @Query("SELECT * FROM exam_schedules WHERE class_id_fk = :classId")
    fun getExamSchedulesByClassId(classId: Int): Flow<List<ExamScheduleEntity>>

    @Query("SELECT * FROM exam_schedules WHERE exam_paper_id_fk = :paperId")
    fun getExamSchedulesByPaperId(paperId: Int): Flow<List<ExamScheduleEntity>>
}
