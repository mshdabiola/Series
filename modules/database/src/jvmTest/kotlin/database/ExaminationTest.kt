package database

import com.mshdabiola.series_database.dao.ExaminationDao
import com.mshdabiola.series_database.dao.SubjectDao
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject

class ExaminationTest : AbstractTest() {

    private val examinationDao by inject<ExaminationDao>()

    @Before
    fun setUp() = runTest {
        val subjectDao by inject<SubjectDao>()
    }

    @Test
    override fun insert() = runTest {
    }

    @Test
    override fun delete() = runTest {
    }

    @Test
    override fun getOne() = runTest {
    }

    @Test
    override fun getAll() = runTest {
    }
}
