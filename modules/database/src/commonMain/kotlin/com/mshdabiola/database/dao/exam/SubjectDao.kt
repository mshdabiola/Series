/*
 *abiola 2024
 */

package com.mshdabiola.database.dao.exam

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mshdabiola.database.model.exam.SubjectEntity
import com.mshdabiola.database.model.exam.SubjectWithSeries
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsert(subjectEntity: SubjectEntity): Long

    @Query("SELECT * FROM subject_table")
    fun getAll(): Flow<List<SubjectEntity>>

    @Transaction
    @Query("SELECT * FROM subject_table")
    fun getAllWithSeries(): Flow<List<SubjectWithSeries>>

    @Query("SELECT * FROM subject_table WHERE id = :id")
    fun getOne(id: Long): Flow<SubjectEntity?>

    @Transaction
    @Query("SELECT * FROM subject_table WHERE id = :id")
    fun getOneWithSeries(id: Long): Flow<SubjectWithSeries?>

    @Query("SELECT * FROM subject_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<SubjectEntity>>

    @Query("DELETE FROM subject_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<SubjectEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM subject_table")
    suspend fun clearAll()
}
