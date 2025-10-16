package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.SeriesEntity
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
