package com.mshdabiola.data

import java.io.File
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.createParentDirectories
import kotlin.io.path.exists


internal expect val generalPath: String


object ImageUtil{

    fun newPath(oldPathStr: String, examId: Long): Path {
        val oldPath = File(oldPathStr)
        val time = System.currentTimeMillis()
        val extension = if (oldPath.extension == "svg") "xml" else oldPath.extension
        return getGeneralDir("$time.$extension",examId)
    }

    fun getGeneralDir(name: String, examId: Long): Path {
        val homeDir = Path(generalPath,examId.toString())
        if (homeDir.exists().not()) {
            homeDir.createParentDirectories()
        }
        return Path(homeDir.absolutePathString(), name)
    }


}
