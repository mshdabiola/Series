package com.mshdabiola.model

import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists




object ImageUtil{

    fun newPath(extension: String, examId: Long): File {

        val time = System.currentTimeMillis()
        val extension = if (extension== "svg") "xml" else extension
        return (getGeneralDir("$time.$extension",examId))
    }

    fun getGeneralDir(name: String, examId: Long): File {
        val homeDir = File(generalPath,examId.toString())
        if (homeDir.exists().not()) {
            homeDir.mkdirs()
        }
        return File(homeDir, name)
    }





}
