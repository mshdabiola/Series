package com.mshdabiola.data.repository

import app.cash.sqldelight.db.SqlDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.Security
import com.mshdabiola.model.generalPath
import com.mshdabiola.model.parentPath
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream

actual class DatabaseExportImport actual constructor(
    private val database: SeriesDatabase,
    private val driver: SqlDriver,
) {
    actual suspend fun export(
        examsId: List<Long>,
        path: String,
        name: String,
        version: Int,
        key: String,
    ) {
        withContext(Dispatchers.IO) {
            val pathNew =File(path,name)
            if (pathNew.exists().not()){
                pathNew.mkdirs()
            }

            val dbParent=File(parentPath) .parent
            val dbPath=File(dbParent,"databases/data.db")


//            val dir = File(dbDir)
//            if (dir.exists().not()) {
//                dir.mkdirs()
//            }
            launch {

                val dbOutput = File(pathNew, name)
                dbPath.delete()

//                Android
                driver
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

                Security.encode(dbPath.readBytes(), dbOutput.outputStream(), key)

            }
            launch {
                val imagePath = File(pathNew,"image")
                imagePath.deleteOnExit()
                copyImage(imagePath, examsId)
            }
            launch {
                val versionFile = File(pathNew, "version.txt")
                versionFile.deleteOnExit()
                versionFile.writeText("$version")
            }

        }
    }

    actual suspend fun import(path: String, key: String) {
        withContext(Dispatchers.IO) {
            val dbPath = Path(path)
            Security.decode(dbPath.inputStream(), dbPath.outputStream(), key)

            driver
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
                    val from = File(generalPath, "$it")
                    val to = File(dir.path, "$it")

                    Timber.e("From ${from.path} to ${to.path}")
                    //to.createParentDirectories()

                    from.copyRecursively(to, overwrite = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }

}