/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mshdabiola.database.model.TopicCategoryEntity
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
    suspend fun upsert(category: TopicCategoryEntity): Long

    @Upsert
    suspend fun insertAll(category: List<TopicCategoryEntity>)

    @Query("DELETE FROM topic_category_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM topic_category_table")
    suspend fun clearAll()
}
