package database

import com.mshdabiola.seriesdatabase.ExportImport
import com.mshdabiola.seriesdatabase.asEntity
import com.mshdabiola.seriesdatabase.dao.ExaminationDao
import com.mshdabiola.seriesdatabase.dao.InstructionDao
import com.mshdabiola.seriesdatabase.dao.OptionDao
import com.mshdabiola.seriesdatabase.dao.QuestionDao
import com.mshdabiola.seriesdatabase.dao.SeriesDao
import com.mshdabiola.seriesdatabase.dao.SubjectDao
import com.mshdabiola.seriesdatabase.dao.TopicCategoryDao
import com.mshdabiola.seriesdatabase.dao.TopicDao
import com.mshdabiola.seriesdatabase.dao.UserDao
import com.mshdabiola.seriestesting.examinations
import com.mshdabiola.seriestesting.exportableData
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import org.koin.core.component.inject
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class SubjectTest : AbstractTest() {

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

        val series = seriesDao.getOne(1).first()!!

        val subjects = subjectDao.getAll()
            .map { it.filter { it.seriesId == series.id } }
            .first()

        assertEquals(2, subjects.size)

        seriesDao.delete(series.id!!)

        val subjects2 = subjectDao.getAll()
            .map { it.filter { it.seriesId == series.id } }
            .first()

        assertEquals(0, subjects2.size)
    }

    override fun delete() {
    }

    override fun getOne() {
    }

    override fun getAll() {
    }
}
