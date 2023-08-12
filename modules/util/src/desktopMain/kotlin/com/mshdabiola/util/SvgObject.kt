package com.mshdabiola.util

import com.mshdabiola.util.svg2vector.Svg2Vector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

object SvgObject {
    suspend fun saveImage(
        oldName: String,
        path: String,
        examId: Long,
        subjectId: Long,
        imageType: FileManager.ImageType
    ): String {
        return withContext(Dispatchers.IO) {

            val oldPath = FileManager.path(oldName, subjectId, examId, imageType)
            println("oldPath ${oldPath.path}")
            oldPath.delete()
            val newPath = FileManager.newPath(path, subjectId, examId, imageType)
            val imageFile = File(path)

            if (imageFile.extension == "svg") {
                val fileOutputStream = FileOutputStream(newPath)
                Svg2Vector.parseSvgToXml(File(path), fileOutputStream)
                fileOutputStream.close()
            } else {
                imageFile.copyTo(newPath)
            }


            newPath.name
        }
    }
}