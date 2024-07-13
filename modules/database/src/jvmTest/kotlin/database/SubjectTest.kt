package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.ExaminationDao
import com.mshdabiola.database.dao.SubjectDao
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.SubjectEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SubjectTest : AbstractTest() {

    @Test
    override fun insert() = runTest {

    }

    override fun delete() {
    }

    override fun getOne() {
    }

    override fun getAll() {
    }
}
