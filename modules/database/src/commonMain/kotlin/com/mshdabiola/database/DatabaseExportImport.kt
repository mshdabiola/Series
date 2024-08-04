package com.mshdabiola.database

import com.mshdabiola.generalmodel.Security
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.qualifier
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.inputStream

class DatabaseExportImport(
    private val database: SeriesDatabase,
) : KoinComponent {
    suspend fun export(
        examsId: Set<Long>,
        path: String,
        name: String,
        version: Int,
        key: String,
    ) {
        withContext(Dispatchers.IO) {
            val dir = File(path)
            if (dir.exists().not()) {
                dir.mkdirs()
            }
            launch {
                val dbPath = File(path, name)
                dbPath.delete()

                val db by inject<SeriesDatabase>(qualifier = qualifier("tem"), parameters = { parametersOf(dbPath.path) })

                val exams = database.getExaminationDao()
                    .getByIds(examsId)
                    .first()

                val subject = database.getSubjectDao()
                    .getByIds(exams.map { it.subjectId }.toSet())
                    .first()

                val questions = database.getQuestionDao()
                    .getByExamIds(examsId)
                    .first()

                val options = database.getOptionDao()
                    .getByQuestionIds(questions.mapNotNull { it.id }.toSet())
                    .first()

                val instructions = database.getInstructionDao()
                    .getByIds(questions.mapNotNull { it.instructionId }.toSet())
                    .first()

                val topics = database.getTopicDao()
                    .getByIds(questions.mapNotNull { it.topicId }.toSet())
                    .first()

                subject.forEach {
                    db.getSubjectDao().upsert(it)
                }

                exams.forEach {
                    db.getExaminationDao().upsert(it)
                }

                questions.forEach {
                    db.getQuestionDao().upsert(it)
                }

                options.forEach {
                    db.getOptionDao().upsert(it)
                }

                topics.forEach {
                    db.getTopicDao().upsert(it)
                }

                instructions.forEach {
                    db.getInstructionDao().upsert(it)
                }

                db.close()
                Security.encode(dbPath.readBytes(), dbPath.outputStream(), key)
            }
            launch {
                val imagePath = File(path, "image")
                imagePath.deleteOnExit()
                copyImage(imagePath, examsId)
            }
            launch {
                val versionFile = File(dir, "version.txt")
                versionFile.deleteOnExit()
                versionFile.writeText("$version")
            }
        }
    }

    suspend fun import(path: String, key: String) {
        withContext(Dispatchers.IO) {
            val dbPath = Path(path)
            val dbOut = File(File(path).parent, "out.db")
            Security.decode(dbPath.inputStream(), dbOut.outputStream(), key)

            val input by inject<SeriesDatabase>(qualifier = qualifier("tem"), parameters = { parametersOf(dbOut.path) })

            val exams = input.getExaminationDao()
                .getAll()
                .first()
            val subject =
                input.getSubjectDao()
                    .getAll()
                    .first()

            val questions =
                input.getQuestionDao()
                    .getAll()
                    .first()

            val options =
                input.getOptionDao()
                    .getAll()
                    .first()

            val instructions =
                input.getInstructionDao()
                    .getAll()
                    .first()

            val topics =
                input.getTopicDao()
                    .getAll()
                    .first()

            subject.forEach {
                database.getSubjectDao().upsert(it)
            }

            exams.forEach {
                println("exam $it")
                database.getExaminationDao().upsert(it)
            }

            questions.forEach {
                database.getQuestionDao().upsert(it)
            }

            options.forEach {
                database.getOptionDao().upsert(it)
            }

            topics.forEach {
                database.getTopicDao().upsert(it)
            }

            instructions.forEach {
                database.getInstructionDao().upsert(it)
            }

            input.close()
        }
    }

    private suspend fun copyImage(dir: File, examsId: Set<Long>) {
        withContext(Dispatchers.IO) {
            try {
                examsId.forEach {
                    val from = File(generalPath, "image/$it")
                    val to = File(dir.path, "$it")
                    // to.createParentDirectories()
                    if (from.exists()) {
                        from.copyRecursively(to, overwrite = true)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
