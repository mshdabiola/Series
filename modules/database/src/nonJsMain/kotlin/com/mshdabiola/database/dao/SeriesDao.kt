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
import com.mshdabiola.database.model.SeriesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series_table")
    fun getAll(): Flow<List<SeriesEntity>>

    @Query("SELECT * FROM series_table WHERE id = :id")
    fun getOne(id: Long): Flow<SeriesEntity?>

    @Query("SELECT * FROM series_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<SeriesEntity>>

    @Query("DELETE FROM series_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Query("DELETE FROM series_table")
    suspend fun clearAll()

    @Upsert
    suspend fun upsert(seriesEntity: SeriesEntity): Long

    @Upsert
    suspend fun insertAll(users: List<SeriesEntity>)
}
