package com.mshdabiola.data.repository

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.Security
import com.mshabiola.database.model.listOfValueAdapter
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.generalPath
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.nio.file.Path
import java.util.Properties
import kotlin.io.path.ExperimentalPathApi
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.copyToRecursively
import kotlin.io.path.createParentDirectories
import kotlin.io.path.deleteIfExists
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream
import kotlin.io.path.pathString
import kotlin.io.path.readBytes

actual class DatabaseExportImport actual constructor(val database: SeriesDatabase) {
    actual suspend fun export(
        examsId: List<Long>,
        path: String,
        name:String,
        version: Int,
        key: String
    ) {
        withContext(Dispatchers.IO) {
            val dir=File(path)
            if (dir.exists().not()){
                dir.mkdirs()
            }
            launch {

                val dbPath = File(path, name)
                dbPath.delete()

                val driver = JdbcSqliteDriver(
                    "jdbc:sqlite:${dbPath.path}",
                    properties = Properties().apply { put("foreign_keys", "true") }
                )
                    .also { SeriesDatabase.Schema.create(it) }
                driver.execute(null, "PRAGMA user_version=$version", 0)

                val db = SeriesDatabase(
                    driver = driver,
                    questionEntityAdapter = QuestionEntity.Adapter(
                        listOfValueAdapter,
                        listOfValueAdapter
                    ),
                    instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                    optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
                )

                val exams = database.examQueries
                    .getByIds(examsId)
                    .executeAsList()

                val subject = database.subjectQueries
                    .getByIds(exams.map { it.subjectId })
                    .executeAsList()

                val questions = database.questionQueries
                    .getByIds(examsId)
                    .executeAsList()
                val options = database.optionQueries
                    .getByIds(examsId)
                    .executeAsList()

                val instructions = database.instructionQueries
                    .getByIds(questions.mapNotNull { it.instructionId }.distinct())
                    .executeAsList()

                val topics = database.topicQueries
                    .getByIds(questions.mapNotNull { it.topicId }.distinct())
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

                Security.encode(dbPath.readBytes(), dbPath.outputStream(), key)

            }
            launch {
                val imagePath=File(path, "image")
                imagePath.deleteOnExit()
                copyImage(imagePath, examsId)
            }
            launch {
                val versionFile=File(dir,"version.txt")
                versionFile.deleteOnExit()
                versionFile.writeText("$version")
            }

        }
    }

    actual suspend fun import(path: String, key: String) {
        withContext(Dispatchers.IO) {
            val dbPath = Path(path)
            Security.decode(dbPath.inputStream(), dbPath.outputStream(), key)


            val driver = JdbcSqliteDriver(
                "jdbc:sqlite:${dbPath.pathString}",
                properties = Properties().apply { put("foreign_keys", "true") }
            )
                .also { SeriesDatabase.Schema.create(it) }
            val input = SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )

            val exams = input.examQueries
                .getAll()
                .executeAsList()

            val subject = input.subjectQueries
                .getAll()
                .executeAsList()

            val questions = input.questionQueries
                .getAll()
                .executeAsList()
            val options = input.optionQueries
                .getAll()
                .executeAsList()

            val instructions = input.instructionQueries
                .getAll()
                .executeAsList()

            val topics = input.topicQueries
                .getAll()
                .executeAsList()

            subject.forEach {
                database.subjectQueries.insertOrReplace(it)
            }

            exams.forEach {
                database.examQueries.insertOrReplace(it)
            }

            questions.forEach {
                database.questionQueries.insertReplace(it)
            }

            options.forEach {
                database.optionQueries.insertOrReplace(it)
            }

            topics.forEach {
                database.topicQueries.insertOrReplace(it)
            }

            instructions.forEach {
                database.instructionQueries.insertOrReplace(it)
            }

            driver.close()
        }
    }


    suspend fun copyImage(dir: File, examsId: List<Long>) {
        withContext(Dispatchers.IO) {
            try {
                examsId.forEach {
                    val from = File(generalPath,"$it")
                    val to = File(dir.path, "$it")
                    //to.createParentDirectories()

                    from.copyRecursively(to, overwrite = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }


}