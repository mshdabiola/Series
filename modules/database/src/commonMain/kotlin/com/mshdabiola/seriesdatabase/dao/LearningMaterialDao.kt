package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.LearningMaterialEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LearningMaterialDao {
    @Upsert
    suspend fun upsert(material: LearningMaterialEntity): Long

    @Upsert
    suspend fun upsertAll(materials: List<LearningMaterialEntity>)

    @Delete
    suspend fun delete(material: LearningMaterialEntity)

    @Query("DELETE FROM learning_materials")
    suspend fun deleteAllLearningMaterials()

    @Query("SELECT * FROM learning_materials WHERE learning_material_id = :materialId")
    fun getLearningMaterialById(materialId: Int): Flow<LearningMaterialEntity?>

    @Query("SELECT * FROM learning_materials")
    fun getAllLearningMaterials(): Flow<List<LearningMaterialEntity>>

    @Query("SELECT * FROM learning_materials WHERE lesson_topic_id_fk = :topicId")
    fun getLearningMaterialsByTopicId(topicId: Int): Flow<List<LearningMaterialEntity>>
}
