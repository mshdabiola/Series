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
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.relation.ExaminationWithSubjectRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface ExaminationDao {

    @Upsert
    suspend fun upsert(examinationEntity: ExaminationEntity): Long

    @Transaction
    @Query("SELECT * FROM examination_table")
    fun getAllWithSubject(): Flow<List<ExaminationWithSubjectRelation>>

    @Query("SELECT * FROM examination_table")
    fun getAll(): Flow<List<ExaminationEntity>>

    @Transaction
    @Query("SELECT * FROM examination_table WHERE subjectId = :subjectId")
    fun getAllBySubjectIdWithSubject(subjectId: Long): Flow<List<ExaminationWithSubjectRelation>>

    @Transaction
    @Query("SELECT * FROM examination_table WHERE id = :id")
    fun getOneWithSubject(id: Long): Flow<ExaminationWithSubjectRelation?>

    @Query("SELECT * FROM examination_table WHERE id IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<ExaminationEntity>>

    @Query("DELETE FROM examination_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<ExaminationEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM examination_table")
    suspend fun clearAll()
}
