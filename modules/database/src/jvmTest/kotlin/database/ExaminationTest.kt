package database

import app.cash.turbine.test
import com.mshdabiola.database.dao.exam.ExaminationDao
import com.mshdabiola.database.dao.exam.SubjectDao
import com.mshdabiola.database.model.exam.ExaminationEntity
import com.mshdabiola.database.model.exam.SubjectEntity
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
        subjectDao.upsert(SubjectEntity(null, title = "Math"))
        subjectDao.upsert(SubjectEntity(null, title = "English"))
    }

    @Test
    override fun insert()= runTest {
        examinationDao.upsert(ExaminationEntity(null, 1, 2014, false, 45, 54,""))

        examinationDao.getAll()
            .test {
                val list = awaitItem()
                assertEquals(1, list.size)
            }
    }

    @Test
    override fun delete() = runTest{
        examinationDao.upsert(ExaminationEntity(null, 1, 2014, false, 45, 54,""))
        examinationDao.delete(1)
        examinationDao.getAll()
            .test {
                val list = awaitItem()
                assertEquals(0, list.size)
            }
    }

    @Test
    override fun getOne() = runTest{
        examinationDao.upsert(ExaminationEntity(null, 1, 2014, false, 45, 54,""))
        examinationDao.upsert(ExaminationEntity(null, 1, 2016, false, 45, 54,""))

        examinationDao.getOne(2)
            .test {
                val exam = awaitItem()
                assertEquals(2016,exam?.examinationEntity?.year)
            }
    }

    @Test
    override fun getAll()= runTest{
        examinationDao.upsert(ExaminationEntity(null, 1, 2014, false, 45, 54,""))
        examinationDao.upsert(ExaminationEntity(null, 1, 2016, false, 45, 54,""))

        examinationDao.getAll()
            .test {
                val list = awaitItem()
                assertEquals(2,list.size)
            }
    }
}