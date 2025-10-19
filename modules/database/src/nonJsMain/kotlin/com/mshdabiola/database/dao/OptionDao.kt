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
import androidx.room.Upsert
import com.mshdabiola.database.model.OptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OptionDao {

    @Upsert
    suspend fun upsert(optionEntity: OptionEntity): Long

    @Query("SELECT * FROM option_table")
    fun getAll(): Flow<List<OptionEntity>>

    @Query("SELECT * FROM option_table WHERE id = :id")
    fun getOne(id: Long): Flow<OptionEntity?>

//    @Query("SELECT * FROM option_table WHERE examId IN (:ids)")
//    fun getByIds(ids: Set<Long>): Flow<List<OptionEntity>>

    @Query("SELECT * FROM option_table WHERE questionId IN (:ids)")
    fun getByQuestionIds(ids: Set<Long>): Flow<List<OptionEntity>>

    @Query("DELETE FROM option_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<OptionEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM option_table")
    suspend fun clearAll()
}
