package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.SchoolEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SchoolDao {

    @Upsert
    suspend fun upsertAll(schools: List<SchoolEntity>)

    @Upsert
    suspend fun upsert(school: SchoolEntity)

    @Delete
    suspend fun delete(school: SchoolEntity)

    @Query("DELETE FROM schools")
    suspend fun deleteAllSchools()

    @Query("SELECT * FROM schools WHERE school_id = :schoolId")
    fun getSchoolById(schoolId: Int): Flow<SchoolEntity?> // Using Flow for reactive updates

    @Query("SELECT * FROM schools")
    fun getAllSchools(): Flow<List<SchoolEntity>> // Using Flow for reactive updates
}
