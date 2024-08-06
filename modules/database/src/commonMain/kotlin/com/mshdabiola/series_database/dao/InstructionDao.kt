/*
 *abiola 2024
 */

package com.mshdabiola.series_database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.series_database.model.InstructionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InstructionDao {

    @Upsert
    suspend fun upsert(instructionEntity: InstructionEntity): Long

    @Query("SELECT * FROM instruction_table")
    fun getAll(): Flow<List<InstructionEntity>>

    @Query("SELECT * FROM instruction_table WHERE id = :id")
    fun getOne(id: Long): Flow<InstructionEntity?>

    @Query("SELECT * FROM instruction_table WHERE examId IN (:ids)")
    fun getByIds(ids: Set<Long>): Flow<List<InstructionEntity>>

    @Query("DELETE FROM instruction_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<InstructionEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM instruction_table")
    suspend fun clearAll()

    @Query("SELECT * FROM instruction_table WHERE examId = :examId")
    fun getAllByExamId(examId: Long): Flow<List<InstructionEntity>>
}
