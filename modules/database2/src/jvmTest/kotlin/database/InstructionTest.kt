package database

import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesdatabase.dao.SubjectDao
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
    }

    @Test
    override fun delete() = runTest {
    }

    @Test
    override fun getOne() {
    }

    @Test
    override fun getAll() {
    }
}
