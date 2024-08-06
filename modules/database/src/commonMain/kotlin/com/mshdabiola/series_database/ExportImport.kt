package com.mshdabiola.series_database

import com.mshdabiola.series_model.ExportableData
import com.mshdabiola.series_model.Security
import com.mshdabiola.series_model.unzipFile
import com.mshdabiola.series_model.zipDirectory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.InputStream
import java.io.OutputStream

class ExportImport(
    private val database: SeriesDatabase,
) {
    suspend fun export(
        examsId: Set<Long>,
        outputStream: OutputStream,
        password: String,
    ) {
        withContext(Dispatchers.IO) {
            val tempFile = createTempFolder()
            val job = launch {
                // val db by inject<SeriesDatabase>(qualifier = qualifier("tem"), parameters = { parametersOf(dbPath.path) })

                val exams = async {
                    database.getExaminationDao()
                        .getByIds(examsId)
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val subject = async {
                    database.getSubjectDao()
                        .getByIds(exams.await().map { it.subjectId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val series = async {
                    database.getSeriesDao()
                        .getByIds(subject.await().map { it.seriesId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val user = async {
                    database.getUserDao()
                        .getByIds(series.await().map { it.userId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val questions = async {
                    database.getQuestionDao()
                        .getByExamIds(examsId)
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val options = async {
                    database.getOptionDao()
                        .getByQuestionIds(questions.await().map { it.id }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val instructions = async {
                    database.getInstructionDao()
                        .getByIds(questions.await().mapNotNull { it.instructionId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val topics = async {
                    database.getTopicDao()
                        .getByIds(questions.await().mapNotNull { it.topicId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val categories = async {
                    database.getTopicCategoryDao()
                        .getByIds(topics.await().map { it.categoryId }.toSet())
                        .map { list -> list.map { it.asModel() } }
                        .first()
                }

                val data = ExportableData(
                    users = user.await(),
                    series = series.await(),
                    subjects = subject.await(),
                    topics = topics.await(),
                    examinations = exams.await(),
                    instructions = instructions.await(),
                    topicCategory = categories.await(),
                    questions = questions.await(),
                    options = options.await(),

                )

                val string = Security.encodeData(data)

                val output = File(tempFile, "data.json")
                output.createNewFile()

                output.writeText(string)
            }
            val job2 = launch {
                val imagePath = File(tempFile, "image")
                copyImage(imagePath, examsId)
            }

            job.join()
            job2.join()
            if (tempFile != null) {
                zipDirectory(tempFile, outputStream, password)
            }
        }
    }

    suspend fun import(inputStream: InputStream, key: String) {
        withContext(Dispatchers.IO) {
            val tempFile = createTempFolder()
            if (tempFile != null) {
                unzipFile(inputStream, tempFile.path, key)

                launch {
                    val temp = File(tempFile, "data.json").toPath()

                    val data = Security.decodeData(temp)

                    val userJob = launch {
                        database
                            .getUserDao()
                            .insertAll(
                                data
                                    .users
                                    .map { it.asEntity() },
                            )
                    }

                    userJob.join()

                    val seriesJob = launch {
                        database
                            .getSeriesDao()
                            .insertAll(
                                data
                                    .series
                                    .map { it.asEntity() },
                            )
                    }

                    seriesJob.join()

                    val subjectJob = launch {
                        database
                            .getSubjectDao()
                            .insertAll(
                                data
                                    .subjects
                                    .map { it.asEntity() },
                            )
                    }

                    subjectJob.join()

                    launch {
                        database
                            .getTopicCategoryDao()
                            .insertAll(
                                data
                                    .topicCategory
                                    .map { it.asEntity() },
                            )
                        launch {
                            database
                                .getTopicDao()
                                .insertAll(
                                    data
                                        .topics
                                        .map { it.asEntity() },
                                )
                        }
                    }

                    launch {
                        database
                            .getExaminationDao()
                            .insertAll(
                                data
                                    .examinations
                                    .map { it.asEntity() },
                            )

                        launch {
                            database
                                .getInstructionDao()
                                .insertAll(
                                    data
                                        .instructions
                                        .map { it.asEntity() },
                                )
                        }

                        launch {
                            database
                                .getQuestionDao()
                                .insertAll(
                                    data
                                        .questions
                                        .map { it.asEntity() },
                                )

                            launch {
                                database
                                    .getOptionDao()
                                    .insertAll(
                                        data
                                            .options
                                            .map { it.asEntity() },
                                    )
                            }
                        }
                    }
                }
                launch {
                    val imagePath = File(tempFile, "image")
                    imagePath.listFiles()?.forEach {
                        it.copyRecursively(File(generalPath, "image/${it.name}"), true)
                    }
                }
            }
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

    private fun createTempFolder(): File? {
        val tempFolder = File.createTempFile("temp", "", null)
        if (tempFolder.delete() && tempFolder.mkdirs()) {
            return tempFolder
        }
        return null
    }
}
