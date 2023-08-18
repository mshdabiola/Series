package com.mshdabiola.data

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.Security
import com.mshabiola.database.Security.decode
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

object DatabaseUtil {

    //private const val passwordString = "Swordfish"

    suspend fun import(
        output : SeriesDatabase,
        path :String,
        key:String
    ){
        withContext(Dispatchers.IO){
            val dbPath = Path(path)
            decode(dbPath.inputStream(),dbPath.outputStream(),key)



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
                .getAll()
                .executeAsList()

            val subject=input.subjectQueries
                .getAll()
                .executeAsList()

            val questions =input.questionQueries
                .getAll()
                .executeAsList()
            val options=input.optionQueries
                .getAll()
                .executeAsList()

            val instructions=input.instructionQueries
                .getAll()
                .executeAsList()

            val topics=input.topicQueries
                .getAll()
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
        path :String,
        version:Int,
        key:String
    ){
        withContext(Dispatchers.IO){
             launch {
                 val dbPath = Path(path, Constant.assetData)
                 dbPath.deleteIfExists()

                 val driver = JdbcSqliteDriver(
                     "jdbc:sqlite:${dbPath.pathString}",
                     properties = Properties().apply { put("foreign_keys", "true") }
                 )
                     .also { SeriesDatabase.Schema.create(it) }
                 driver.execute(null, "PRAGMA user_version=$version", 0)

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

                 Security.encode(dbPath.readBytes(), dbPath.outputStream(), key)

             }
            launch {
               copyImage(Path(path,"image"),examsId)
            }

            }

        }

    @OptIn(ExperimentalPathApi::class)
    suspend fun copyImage(dir:Path, examsId: List<Long>) {
        withContext(Dispatchers.IO) {
            try {
                examsId.forEach {
                    val from = Path(generalPath,"$it")
                    val to = Path(dir.absolutePathString(), "$it")
                    to.createParentDirectories()

                    from.copyToRecursively(to, overwrite = true, followLinks = true)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }


        }
    }


    }

