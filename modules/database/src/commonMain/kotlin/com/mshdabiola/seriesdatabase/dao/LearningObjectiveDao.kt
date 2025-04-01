package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.LearningObjectiveEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningObjectiveDao {
    @Upsert
    suspend fun upsert(objective: LearningObjectiveEntity): Long

    @Upsert
    suspend fun upsertAll(objectives: List<LearningObjectiveEntity>)

    @Delete
    suspend fun delete(objective: LearningObjectiveEntity)

    @Query("DELETE FROM learning_objectives")
    suspend fun deleteAllLearningObjectives()

    @Query("SELECT * FROM learning_objectives WHERE learning_objective_id = :objectiveId")
    fun getLearningObjectiveById(objectiveId: Int): Flow<LearningObjectiveEntity?>

    @Query("SELECT * FROM learning_objectives")
    fun getAllLearningObjectives(): Flow<List<LearningObjectiveEntity>>

    @Query("SELECT * FROM learning_objectives WHERE lesson_topic_id_fk = :topicId")
    fun getLearningObjectivesByTopicId(topicId: Int): Flow<List<LearningObjectiveEntity>>
}
