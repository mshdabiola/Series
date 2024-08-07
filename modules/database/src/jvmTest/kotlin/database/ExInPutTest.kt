package database

import com.mshdabiola.seriesdatabase.ExportImport
import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.asModel
import com.mshdabiola.seriesdatabase.dao.ExaminationDao
import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesdatabase.dao.OptionDao
import com.mshdabiola.seriesdatabase.dao.QuestionDao
import com.mshdabiola.seriesdatabase.dao.SeriesDao
import com.mshdabiola.seriesdatabase.dao.SubjectDao
import com.mshdabiola.seriesdatabase.dao.TopicCategoryDao
import com.mshdabiola.seriesdatabase.dao.TopicDao
import com.mshdabiola.seriesdatabase.dao.UserDao
import com.mshdabiola.seriesdatabase.generalPath
import com.mshdabiola.seriestesting.defaultData
import com.mshdabiola.seriestesting.examinations
import com.mshdabiola.seriestesting.exportableData
import com.mshdabiola.seriestesting.insertData
import com.mshdabiola.seriestesting.instructions
import com.mshdabiola.seriestesting.options
import com.mshdabiola.seriestesting.questionsPlain
import com.mshdabiola.seriestesting.series
import com.mshdabiola.seriestesting.subjects
import com.mshdabiola.seriestesting.topicCategories
import com.mshdabiola.seriestesting.topics
import com.mshdabiola.seriestesting.users
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import java.io.File
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class ExInPutTest : AbstractTest() {

    @Test
    fun setData() = runTest {
        insertData()
        val userDao by inject<UserDao>()

        val users = userDao.getAllUsers().first()

        assertEquals(defaultData.users.toMutableList().apply { removeFirst() }, users.map { it.asModel() })
    }

    @Test
    override fun insert() = runTest {
        // val modelDao by inject<NoteDao>()
        val exportImport by inject<ExportImport>()
        val userDao by inject<UserDao>()
        val seriesDao by inject<SeriesDao>()
        val subjectDao by inject<SubjectDao>()
        val examinationDao by inject<ExaminationDao>()
        val instructionDao by inject<InstructionDao>()
        val questionDao by inject<QuestionDao>()
        val optionDao by inject<OptionDao>()
        val topicDao by inject<TopicDao>()
        val topicCategoryDao by inject<TopicCategoryDao>()

        val data = exportableData

        userDao.insertAll(users.map { it.asEntity() })
        seriesDao.insertAll(series.map { it.asEntity() })
        subjectDao.insertAll(subjects.map { it.asEntity() })
        examinationDao.insertAll(examinations.map { it.asEntity() })
        instructionDao.insertAll(instructions.map { it.asEntity() })
        questionDao.insertAll(questionsPlain.map { it.asEntity() })
        optionDao.insertAll(options.map { it.asEntity() })
        topicCategoryDao.insertAll(topicCategories.map { it.asEntity() })
        topicDao.insertAll(topics.map { it.asEntity() })

        val path = "/home/mshdabiola/StudioProjects/Series/output2.se"

        generalPath = "/home/mshdabiola/StudioProjects/Series/test2"
        val output = File(path)
        output.createNewFile()

        output.outputStream().use {
            exportImport.export(examsId = examinations.map { it.id }.toSet(), it, "abiola")
        }
    }

    @Test
    override fun delete() = runTest {
        val path = "/home/mshdabiola/StudioProjects/Series/output2.se"
        val exportImport by inject<ExportImport>()

        val input = File(path).inputStream()
        val dir = File("/home/mshdabiola/StudioProjects/Series/output")
        dir.mkdirs()
        generalPath = dir.absolutePath
        exportImport.import(input, "abiola")
    }

    @Test
    override fun getOne() {
        val path = "/home/mshdabiola/StudioProjects/Series"
        val file = File("/home/mshdabiola/StudioProjects/Series/test2")
        file.mkdirs()
        val dest = File(path, "test")
        //   unzipFile(dest.absolutePath, file.absolutePath, "abiola")
    }

    override fun getAll() {
    }
}
