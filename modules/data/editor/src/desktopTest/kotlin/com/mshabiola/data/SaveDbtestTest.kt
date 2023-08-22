package com.mshabiola.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.Security
import com.mshabiola.database.di.databaseModule
import com.mshabiola.database.di.name
import com.mshabiola.database.model.listOfValueAdapter
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.data.Item
import commshdabioladatabase.tables.ExamEntity
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import commshdabioladatabase.tables.SubjectEntity
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

class DatabaseUtilTest : KoinTest {


    @get:Rule
    val koinTestRule = KoinTestRule.create {
        val module = module {
            single(qualifier = qualifier("real")) {
                val driver = JdbcSqliteDriver(
                    JdbcSqliteDriver.IN_MEMORY,
                    properties = Properties().apply { put("foreign_keys", "true") }
                )
                    .also { SeriesDatabase.Schema.create(it) }

                SeriesDatabase(
                    driver = driver,
                    questionEntityAdapter = QuestionEntity.Adapter(
                        listOfValueAdapter,
                        listOfValueAdapter
                    ),
                    instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                    optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
                )
            }

        }
        // Your KoinApplication instance here
        modules(module, databaseModule)

    }

    @Test
    fun insert() = runTest {

        val inputDb by inject<SeriesDatabase>(qualifier = qualifier(name))

        val subjects = listOf(
            SubjectEntity(1, "Math"),
            SubjectEntity(2, "English")
        )

        val exams = listOf(
            ExamEntity(1, 1, 2012, 0),
            ExamEntity(2, 1, 2014, 1),
            ExamEntity(3, 1, 2016, 1),
            ExamEntity(4, 1, 2017, 0),
        )
        val quetions = listOf(
            QuestionEntity(
                id = 1,
                nos = 1,
                examId = 1,
                content = listOf(Item("What is your name")),
                isTheory = 0,
                answer = null,
                instructionId = null,
                topicId = null
            ),
            QuestionEntity(
                id = 2,
                nos = 2,
                examId = 2,
                content = listOf(Item("What is your name")),
                isTheory = 0,
                answer = null,
                instructionId = null,
                topicId = null
            ),
            QuestionEntity(
                id = 3,
                nos = 1,
                examId = 4,
                content = listOf(Item("What is your name")),
                isTheory = 0,
                answer = null,
                instructionId = null,
                topicId = null
            ),
            QuestionEntity(
                id = 4,
                nos = 1,
                examId = 2,
                content = listOf(Item("What is your name")),
                isTheory = 0,
                answer = null,
                instructionId = null,
                topicId = null
            ),

            )


        val options = listOf(
            OptionEntity(
                id = -1,
                nos = 1,
                questionId = 1,
                examId = 1,
                content = listOf(Item("abioa")),
                isAnswer = 1
            ),
            OptionEntity(
                id = -1,
                nos = 2,
                questionId = 1,
                examId = 1,
                content = listOf(Item("abioa")),
                isAnswer = 0
            ),
            OptionEntity(
                id = -1,
                nos = 3,
                questionId = 1,
                examId = 1,
                content = listOf(Item("abioa")),
                isAnswer = 0
            )
        )

        subjects.forEach {
            inputDb.subjectQueries.insert(it)
        }
        exams.forEach {
            inputDb.examQueries.insert(it)
        }
        quetions.forEach {
            inputDb.questionQueries.insert(it)
        }
        options.forEach {
            inputDb.optionQueries.insert(it)
        }

//        DatabaseUtil.export(
//            inputDb,
//            listOf(1, 2, 3, 4),
//            "/Users/user/AndroidStudioProjects/Series/subject",
//            2,
//            key = Constant.defaultKey
//        )


    }

    @Test
    fun readIt() = runTest {
        val path =
            Path("/Users/user/AndroidStudioProjects/Series/subject/${Constant.assetData}test")
        val output =
            Path("/Users/user/AndroidStudioProjects/Series/subject/${Constant.databaseName}")
        Security.decode(path.inputStream(), output.outputStream(), key = Constant.defaultKey)
    }
}

