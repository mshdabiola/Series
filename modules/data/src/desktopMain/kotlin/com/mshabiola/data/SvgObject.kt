package com.mshabiola.data

import com.mshdabiola.data.ImageUtil.getGeneralDir
import com.mshdabiola.data.ImageUtil.newPath
//import com.android.ide.common.vectordrawable.Svg2Vector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.io.path.Path
import kotlin.io.path.absolutePathString
import kotlin.io.path.copyTo
import kotlin.io.path.deleteIfExists
import kotlin.io.path.extension
import kotlin.io.path.name
import kotlin.io.path.outputStream

object SvgObject {
    suspend fun saveImage(
        oldName: String, //old image
        newImagePath: String, //new image
        examId: Long,

        ): String {
        return withContext(Dispatchers.IO) {

            val oldPath = getGeneralDir(oldName,examId)
            println("oldPath ${oldPath.absolutePathString()}")
            oldPath.deleteIfExists()

            val newPath = newPath(newImagePath, examId)
           // val imageFile = File(path)

            if (newPath.extension == "svg") {
                val fileOutputStream =newPath.outputStream()
//                Svg2Vector.parseSvgToXml(File(path), fileOutputStream)
               // Svg2Vector.parseSvgToXml(Path(path), fileOutputStream)

                fileOutputStream.close()
            } else {
                Path(newImagePath).copyTo(newPath)
            }


           newPath.name
        }
    }
}