package com.mshdabiola.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.SeriesWithSubjects
import kotlinx.coroutines.flow.Flow

@Dao
interface SeriesDao {
    @Query("SELECT * FROM series_table")
    fun getAll(): Flow<List<SeriesEntity>>

    @Transaction
    @Query("SELECT * FROM series_table")
    fun getAllWithSubjects(): Flow<List<SeriesWithSubjects>>

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