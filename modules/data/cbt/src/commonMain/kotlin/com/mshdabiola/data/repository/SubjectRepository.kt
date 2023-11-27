package com.mshdabiola.data.repository

import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

internal class SubjectRepository(
    private val iSubjectDao: ISubjectDao,
) : ISubjectRepository {


    override fun getAll(): Flow<List<Subject>> {
        return iSubjectDao
            .getAll()
    }
}