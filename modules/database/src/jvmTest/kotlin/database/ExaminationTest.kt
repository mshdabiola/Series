package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.SubjectEntity
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject
import kotlin.test.assertEquals

class ExaminationTest :AbstractTest() {

    private val examinationDao by inject<ExaminationDao>()

    @Before
    fun setUp() = runTest{
        val subjectDao by inject<SubjectDao>()
    }

    @Test
    override fun insert()= runTest {

    }

    @Test
    override fun delete() = runTest{

    }

    @Test
    override fun getOne() = runTest{

    }

    @Test
    override fun getAll()= runTest{

    }
}