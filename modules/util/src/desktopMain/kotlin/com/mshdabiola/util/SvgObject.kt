package com.mshdabiola.util
//
//import com.mshdabiola.model.svg2vector.Svg2Vector
////import com.android.ide.common.vectordrawable.Svg2Vector
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.withContext
//import java.io.File
//import java.io.FileOutputStream
//import kotlin.io.path.Path
//
//object SvgObject {
//    suspend fun saveImage(
//        oldName: String,
//        path: String,
//        examId: Long,
//
//    ): String {
//        return withContext(Dispatchers.IO) {
//
//            val oldPath = FileManager.path(oldName, subjectId, examId, imageType)
//            println("oldPath ${oldPath.path}")
//            oldPath.delete()
//            val newPath = FileManager.newPath(path, subjectId, examId, imageType)
//            val imageFile = File(path)
//
//            if (imageFile.extension == "svg") {
//                val fileOutputStream = FileOutputStream(newPath)
////                Svg2Vector.parseSvgToXml(File(path), fileOutputStream)
//                Svg2Vector.parseSvgToXml(Path(path), fileOutputStream)
//
//                fileOutputStream.close()
//            } else {
//                imageFile.copyTo(newPath)
//            }
//
//
//            newPath.name
//        }
//    }
//}