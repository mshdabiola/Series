package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.GradeLevelEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface GradeLevelDao {

    @Upsert
    suspend fun upsert(gradeLevel: GradeLevelEntity): Long

    @Upsert
    suspend fun upsertAll(gradeLevels: List<GradeLevelEntity>)

    @Delete
    suspend fun delete(gradeLevel: GradeLevelEntity)

    @Query("DELETE FROM grade_levels")
    suspend fun deleteAllGradeLevels()

    @Query("SELECT * FROM grade_levels WHERE grade_level_id = :gradeLevelId")
    fun getGradeLevelById(gradeLevelId: Int): Flow<GradeLevelEntity?>

    @Query("SELECT * FROM grade_levels")
    fun getAllGradeLevels(): Flow<List<GradeLevelEntity>>
}
