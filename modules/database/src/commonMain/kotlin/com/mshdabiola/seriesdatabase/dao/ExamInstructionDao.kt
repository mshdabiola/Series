package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExamInstructionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExamInstructionDao {
    @Delete
    suspend fun delete(examInstructionEntity: ExamInstructionEntity)

    @Upsert
    suspend fun upsert(examInstructionEntity: ExamInstructionEntity)

    @Insert
    suspend fun upsertAll(examInstructionEntity: List<ExamInstructionEntity>)

    @Query("SELECT * FROM instruction_table WHERE id=:id")
    fun getExamInstruction(id: Long): Flow<ExamInstructionEntity>
}
