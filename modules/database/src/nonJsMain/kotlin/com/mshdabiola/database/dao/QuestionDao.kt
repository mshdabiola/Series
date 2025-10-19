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
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.relation.QuestionWithOptsInstTopRelation
import kotlinx.coroutines.flow.Flow

@Dao
interface QuestionDao {

    @Upsert
    suspend fun upsert(questionEntity: QuestionEntity): Long

    @Transaction
    @Query("SELECT * FROM question_table")
    fun getAllWithOptsInstTop(): Flow<List<QuestionWithOptsInstTopRelation>>

    @Query("SELECT * FROM question_table")
    fun getAll(): Flow<List<QuestionEntity>>

    @Transaction
    @Query("SELECT * FROM question_table WHERE id = :id")
    fun getOneWithOptsInstTop(id: Long): Flow<QuestionWithOptsInstTopRelation?>

    @Query("SELECT * FROM question_table WHERE examId IN (:ids)")
    fun getByExamIds(ids: Set<Long>): Flow<List<QuestionEntity>>

    @Transaction
    @Query("SELECT * FROM question_table WHERE examId = :examId")
    fun getByExamId(examId: Long): Flow<List<QuestionWithOptsInstTopRelation>>

//    @Transaction
//    @Query("SELECT * FROM question_table WHERE isTheory = false ORDER BY RANDOM() LIMIT :number")
//    fun getByRandom(number: Long): Flow<List<QuestionWithOptsInstTop>>

    @Query("DELETE FROM question_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<QuestionEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM question_table")
    suspend fun clearAll()
}
