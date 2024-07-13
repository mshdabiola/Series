package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.TopicCategoryEntity
import com.mshdabiola.database.model.UserEntity
import com.mshdabiola.database.model.relation.CategoryWithTopicsRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicCategoryDao {

    @Query("SELECT * FROM topic_category_table")
    fun getAll(): Flow<List<TopicCategoryEntity>>

    @Query("SELECT * FROM topic_category_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<TopicCategoryEntity>>
    @Transaction
    @Query("SELECT * FROM topic_category_table WHERE id = :id")
    fun getOneWithTopics(id: Long): Flow<CategoryWithTopicsRelation?>

    @Transaction
    @Query("SELECT * FROM topic_category_table WHERE subjectId = :subjectId")
    fun getAllWithTopicsBySubjectId(subjectId: Long): Flow<List<CategoryWithTopicsRelation>>

    @Query("SELECT * FROM topic_category_table WHERE id = :subjectId")
    fun getAllBySubjectId(subjectId: Long): Flow<List<TopicCategoryEntity>>

    @Transaction
    @Query("SELECT * FROM topic_category_table")
    fun getAllWithTopics(): Flow<List<CategoryWithTopicsRelation>>

    @Upsert
    suspend fun upsert(category: TopicCategoryEntity) :Long

    @Upsert
    suspend fun insertAll(category: List<TopicCategoryEntity>)

    @Query("DELETE FROM topic_category_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM topic_category_table")
    suspend fun clearAll()

}