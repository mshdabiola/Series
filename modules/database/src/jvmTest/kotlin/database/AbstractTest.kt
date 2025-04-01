package database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.seriesdatabase.SchoolDatabase
import com.mshdabiola.seriesdatabase.di.daoModules
import com.mshdabiola.seriesdatabase.di.getRoomDatabase
import org.junit.Rule
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

abstract class AbstractTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(
            module {
                single(qualifier = qualifier("per")) {
                    val db = Room
                        .inMemoryDatabaseBuilder<SchoolDatabase>()
                        .setDriver(BundledSQLiteDriver())
                    getRoomDatabase(db)
                }

                includes(daoModules)
            },
        )
    }
}
//
// suspend fun KoinComponent.insertData() {
//    val userDao by inject<UserDao>()
//    val seriesDao by inject<SeriesDao>()
//    val subjectDao by inject<SubjectDao>()
//    val examinationDao by inject<ExaminationDao>()
//    val instructionDao by inject<InstructionDao>()
//    val questionDao by inject<QuestionDao>()
//    val optionDao by inject<OptionDao>()
//    val topicDao by inject<TopicDao>()
//    val topicCategoryDao by inject<TopicCategoryDao>()
//
//    userDao.insertAll(defaultData.users.map { it.asEntity() })
//    seriesDao.insertAll(defaultData.series.map { it.asEntity() })
//    subjectDao.insertAll(defaultData.subjects.map { it.asEntity() })
//    examinationDao.insertAll(defaultData.examinations.map { it.asEntity() })
//    instructionDao.insertAll(defaultData.instructions.map { it.asEntity() })
//    questionDao.insertAll(defaultData.questions.map { it.asEntity() })
//    optionDao.insertAll(defaultData.options.map { it.asEntity() })
//    topicCategoryDao.insertAll(defaultData.topicCategory.map { it.asEntity() })
//    topicDao.insertAll(defaultData.topics.map { it.asEntity() })
// }
