/*
 *abiola 2024
 */

package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.TopicEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TopicDao {

    @Upsert
    suspend fun upsert(topicEntity: TopicEntity): Long

    @Query("SELECT * FROM topic_table")
    fun getAll(): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic_table WHERE subjectId = :subjectID")
    fun getAllBySubject(subjectID: Long): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<TopicEntity>>

    @Query("SELECT * FROM topic_table WHERE id = :id")
    fun getOne(id: Long): Flow<TopicEntity?>

    @Query("DELETE FROM topic_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<TopicEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM topic_table")
    suspend fun clearAll()
}
