package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.AcademicStaffEntity
import kotlinx.coroutines.flow.Flow
@Dao
interface AcademicStaffDao {
    @Upsert
    suspend fun upsert(staff: AcademicStaffEntity)

    @Delete
    suspend fun delete(staff: AcademicStaffEntity)

    @Query("DELETE FROM academic_staff")
    suspend fun deleteAllAcademicStaff()

    @Query("SELECT * FROM academic_staff WHERE staff_id = :staffId")
    fun getAcademicStaffById(staffId: Int): Flow<AcademicStaffEntity?>

    @Query("SELECT * FROM academic_staff")
    fun getAllAcademicStaff(): Flow<List<AcademicStaffEntity>>

    @Upsert
    suspend fun upsertAll(staffList: List<AcademicStaffEntity>)
}
