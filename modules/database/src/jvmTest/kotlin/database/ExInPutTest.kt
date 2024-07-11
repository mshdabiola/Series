package database

import app.cash.turbine.test
import com.mshdabiola.database.DatabaseExportImport
import com.mshdabiola.database.dao.exam.ExaminationDao
import com.mshdabiola.database.dao.exam.SubjectDao
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExInPutTest : AbstractTest() {

    @Test
    override fun insert() = runTest {
        // val modelDao by inject<NoteDao>()
        val subjectDao by inject<SubjectDao>()
        val examinationDao by inject<ExaminationDao>()
        val databaseExportImport by inject<DatabaseExportImport>()

//
//        val id = subjectDao.upsert(SubjectEntity(null, title = "abiola"))
//
//        examinationDao.upsert(ExaminationEntity(null, id, 2014, false, 45, 54))
//
//        val path="/home/mshdabiola/StudioProjects/Series/distribution"
//
//        databaseExportImport.export(setOf(1L),path,"output",1,"keykey")

        val path2 = "/home/mshdabiola/StudioProjects/Series/distribution/output"
        databaseExportImport.import(path2, "keykey")
        examinationDao.getAll2()
            .test {
                val list = awaitItem()
                print(list)
                assertEquals(2014, list[0].year)
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
