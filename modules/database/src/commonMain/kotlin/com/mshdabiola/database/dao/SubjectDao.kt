/*
 *abiola 2024
 */

package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.database.model.SubjectEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsert(subjectEntity: SubjectEntity): Long

    @Query("SELECT * FROM subject_table")
    fun getAll(): Flow<List<SubjectEntity>>

    @Query("SELECT * FROM subject_table WHERE id = :id")
    fun getOne(id: Long): Flow<SubjectEntity?>

    @Query("DELETE FROM subject_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<SubjectEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM subject_table")
    suspend fun clearAll()
}
