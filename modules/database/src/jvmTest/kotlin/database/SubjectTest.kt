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
        val subjectDao by inject<SubjectDao>()
        val examinationDao by inject<ExaminationDao>()

        val id = subjectDao.upsert(SubjectEntity(null, title = "abiola"))

        examinationDao.upsert(ExaminationEntity(null, id, 2014, false, 45, 54))

        examinationDao.getAll()
            .test {
                val list = awaitItem()
                print(list)
                assertEquals("abiola", list[0].subjectEntity.title)
                this.cancelAndIgnoreRemainingEvents()
            }
    }

    override fun delete() {
    }

    override fun getOne() {
    }

    override fun getAll() {
    }
}
