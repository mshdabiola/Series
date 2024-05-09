/*
 *abiola 2024
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

    @Query("DELETE FROM option_table WHERE id = :id")
    suspend fun delete(id: Long)

    @Upsert
    suspend fun insertAll(users: List<OptionEntity>)

//    @Query("SELECT * FROM note_table")
//   suspend fun pagingSource(): PagingSource<Int, NoteEntity>

    @Query("DELETE FROM option_table")
    suspend fun clearAll()
}
