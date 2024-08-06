package database

import com.mshdabiola.series_database.dao.InstructionDao
import com.mshdabiola.series_database.dao.SubjectDao
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.koin.core.component.inject

class InstructionTest : AbstractTest() {

    val instructionDao by inject<InstructionDao>()

    @Before
    fun setUp() = runTest {
        val subjectDao by inject<SubjectDao>()
    }

    @Test
    override fun insert() {
        TODO("Not yet implemented")
    }

    @Test
    override fun delete() = runTest {
    }

    @Test
    override fun getOne() {
        TODO("Not yet implemented")
    }

    @Test
    override fun getAll() {
        TODO("Not yet implemented")
    }
}
