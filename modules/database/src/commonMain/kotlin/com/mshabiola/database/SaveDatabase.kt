package com.mshabiola.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Properties
import kotlin.io.path.Path
import kotlin.io.path.pathString

class SaveDatabase() {



    suspend fun import(
        output : SeriesDatabase,
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
            val input=  SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )

            val exams = input.examQueries
                .getByIds(examsId)
                .executeAsList()

            val subject=input.subjectQueries
                .getByIds(exams.map { it.subjectId })
                .executeAsList()

            val questions =input.questionQueries
                .getByIds(examsId)
                .executeAsList()
            val options=input.optionQueries
                .getByIds(examsId)
                .executeAsList()

            val instructions=input.instructionQueries
                .getByIds(questions.mapNotNull { it.instructionId}.distinct())
                .executeAsList()

            val topics=input.topicQueries
                .getByIds(questions.mapNotNull { it.topicId}.distinct())
                .executeAsList()

            subject.forEach {
                output.subjectQueries.insertOrReplace(it)
            }

            exams.forEach {
                output.examQueries.insertOrReplace(it)
            }

            questions.forEach {
                output.questionQueries.insertReplace(it)
            }

            options.forEach {
                output.optionQueries.insertOrReplace(it)
            }

            topics.forEach {
                output.topicQueries.insertOrReplace(it)
            }

            instructions.forEach {
                output.instructionQueries.insertOrReplace(it)
            }

            driver.close()
        }

    }


    suspend fun export(
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

