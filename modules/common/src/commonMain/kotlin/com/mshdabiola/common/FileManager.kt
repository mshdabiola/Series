package com.mshdabiola.common

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

expect  val generalPath :String
class FileManager {

    suspend fun saveImage(
        oldName: String,
        path: String,
        examId: Long,
        subjectId: Long,
        imageType: ImageType
    ): String {
        return withContext(Dispatchers.IO) {

            val oldPath = path(oldName, subjectId, examId, imageType)
            println("oldPath ${oldPath.path}")
            oldPath.delete()
            val newPath = newPath(path, subjectId, examId, imageType)
            val imageFile = File(path)

            imageFile.copyTo(newPath)

            newPath.name
        }
    }

    private fun newPath(path: String, subjectId: Long, examId: Long, imageType: ImageType): File {
        val oldPath = File(path)
        val time = System.currentTimeMillis()
        return path("$time.${oldPath.extension}", subjectId, examId, imageType)
    }

    private fun path(name: String, subjectId: Long, examId: Long, imageType: ImageType): File {
        val home = File(getGeneraPath(subjectId, examId, imageType))
        if (home.exists().not()) {
            home.mkdirs()
        }
        return File(home, name)
    }

    fun getGeneraPath(subjectId: Long, examId: Long, imageType: ImageType): String {
        return "$generalPath/subject/$subjectId/${imageType.name.lowercase()}/$examId"
    }

    fun getSubjectPath(subjectId: Long):String{
        return "$generalPath/subject/$subjectId"
    }

    fun delete(name: String, subjectId: Long, examId: Long, imageType: ImageType) {
        path(name, subjectId, examId, imageType).delete()
    }

    enum class ImageType {
        INSTRUCTION,
        QUESTION
    }


}