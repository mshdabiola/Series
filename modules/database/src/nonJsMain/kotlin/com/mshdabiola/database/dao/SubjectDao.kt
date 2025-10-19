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
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.relation.SubjectWithSeriesRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface SubjectDao {

    @Upsert
    suspend fun upsert(subjectEntity: SubjectEntity): Long

    @Query("SELECT * FROM subject_table")
    fun getAll(): Flow<List<SubjectEntity>>

    @Transaction
    @Query("SELECT * FROM subject_table")
    fun getAllWithSeries(): Flow<List<SubjectWithSeriesRelation>>

    @Query("SELECT * FROM subject_table WHERE id = :id")
    fun getOne(id: Long): Flow<SubjectEntity?>

    @Transaction
    @Query("SELECT * FROM subject_table WHERE id = :id")
    fun getOneWithSeries(id: Long): Flow<SubjectWithSeriesRelation?>

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
