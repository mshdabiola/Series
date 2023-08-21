package com.mshdabiola.data

import com.mshdabiola.model.ImageUtil.getGeneralDir
import com.mshdabiola.model.ImageUtil.newPath
import com.mshdabiola.svgtovector.Svg2Vector
//import com.android.ide.common.vectordrawable.Svg2Vector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.copyTo
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.deleteExisting
import kotlin.io.path.deleteIfExists
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.outputStream

object SvgObject {
    suspend fun saveImage(
        oldName: String, //old image
        fileString: String, //new image
        examId: Long,

        ): String {
        return withContext(Dispatchers.IO) {
            val oldPath = File(getGeneralDir(oldName, examId).path)
            println("oldPath ${oldPath.path}")
            oldPath.delete()
            val imageFile = File(fileString)
            val newPath = newPath(imageFile.extension,examId)



            if (imageFile.extension == "svg") {
                val fileOutputStream = FileOutputStream(newPath)
//                Svg2Vector.parseSvgToXml(File(path), fileOutputStream)
                Svg2Vector.parseSvgToXml(Path(fileString), fileOutputStream)

                fileOutputStream.close()
            } else {
                imageFile.copyTo(newPath)
            }


            newPath.name
        }
    }
}