package com.mshabiola.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.ExamQueries
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.InstructionQueries
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.OptionQueries
import commshdabioladatabase.tables.QuestionEntity
import commshdabioladatabase.tables.QuestionQueries
import commshdabioladatabase.tables.SubjectQueries
import commshdabioladatabase.tables.TopicQueries
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.nio.file.Path
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.pathString

class SaveDatabase() {





    suspend fun insert(
        inputDb : SeriesDatabase,
        examsId : List<Long>,
        path :String
    ){
        withContext(Dispatchers.IO){
             val dbPath = Path(path)

            val driver = JdbcSqliteDriver(
                "jdbc:sqlite:${dbPath.pathString}",
                properties = Properties().apply { put("foreign_keys", "true") }
            )
                .also { SeriesDatabase.Schema.create(it) }
             val db=  SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )

            val exams = inputDb.examQueries
                .getByIds(examsId)
                .executeAsList()

            val subject=inputDb.subjectQueries
                .getByIds(exams.map { it.subjectId })
                .executeAsList()

            val questions =inputDb.questionQueries
                .getByIds(examsId)
                .executeAsList()
            val options=inputDb.optionQueries
                .getByIds(examsId)
                .executeAsList()

            val instructions=inputDb.instructionQueries
                .getByIds(questions.mapNotNull { it.instructionId}.distinct())
                .executeAsList()

            val topics=inputDb.topicQueries
                .getByIds(questions.mapNotNull { it.topicId}.distinct())
                .executeAsList()

            subject.forEach {
                db.subjectQueries.insertOrReplace(it)
            }

            exams.forEach {
                db.examQueries.insertOrReplace(it)
            }

            questions.forEach {
                db.questionQueries.insertReplace(it)
            }

            options.forEach {
                db.optionQueries.insertOrReplace(it)
            }

            topics.forEach {
                db.topicQueries.insertOrReplace(it)
            }

            instructions.forEach {
                db.instructionQueries.insertOrReplace(it)
            }

                driver.close()
            }

        }


    }

