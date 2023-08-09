package com.mshdabiola.util

import java.io.File

expect val generalPath: String

object FileManager {



    fun newPath(path: String, subjectId: Long, examId: Long, imageType: ImageType): File {
        val oldPath = File(path)
        val time = System.currentTimeMillis()
        val extension=if(oldPath.extension=="svg")"xml" else oldPath.extension
        return path("$time.$extension", subjectId, examId, imageType)
    }

    fun path(name: String, subjectId: Long, examId: Long, imageType: ImageType): File {
        val home = File(getGeneraPath(subjectId, examId, imageType))
        if (home.exists().not()) {
            home.mkdirs()
        }
        return File(home, name)
    }

    fun getGeneraPath(subjectId: Long, examId: Long, imageType: ImageType): String {
        return "$generalPath/subject/$subjectId/${imageType.name.lowercase()}/$examId"
    }

    fun getSubjectPath(subjectId: Long): String {
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