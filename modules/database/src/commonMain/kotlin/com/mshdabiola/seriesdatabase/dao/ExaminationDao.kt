/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ExaminationEntity
import com.mshdabiola.seriesdatabase.model.relation.ExaminationWithSubjectRelation
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
