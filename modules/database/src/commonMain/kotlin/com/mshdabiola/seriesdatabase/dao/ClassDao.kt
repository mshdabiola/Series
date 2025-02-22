package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ClassEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ClassDao {
    @Upsert
    suspend fun upsert(clazz: ClassEntity)

    @Upsert
    suspend fun upsertAll(classes: List<ClassEntity>)
    suspend fun update(clazz: ClassEntity)

    @Delete
    suspend fun delete(clazz: ClassEntity)

    @Query("DELETE FROM classes")
    suspend fun deleteAllClasses()

    @Query("SELECT * FROM classes WHERE class_id = :classId")
    fun getClassById(classId: Int): Flow<ClassEntity?>

    @Query("SELECT * FROM classes")
    fun getAllClasses(): Flow<List<ClassEntity>>

    @Query("SELECT * FROM classes WHERE grade_level_id_fk = :gradeLevelId")
    fun getClassesByGradeLevelId(gradeLevelId: Int): Flow<List<ClassEntity>>
}
