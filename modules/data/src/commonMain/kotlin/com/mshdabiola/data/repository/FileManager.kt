package com.mshdabiola.data.repository

import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Type
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

object FileManager {
    val generalPath = "/Users/user/AndroidStudioProjects/Series"

    private suspend fun saveImage(
        path: String,
        examId: Long,
        subjectId: Long
    ): String {
        return withContext(Dispatchers.IO) {

            //System.getProperty("java.io.tmpdir")
            val home = File("$generalPath/subject/$subjectId/instruction/$examId")
            if (home.exists().not()) {
                home.mkdirs()
            }
            println("source $path")
            val pathFile = File(path)
            val curTime = System.currentTimeMillis()
            val newPath =
                File(home, "${pathFile.nameWithoutExtension}_$curTime.${pathFile.extension}")
            pathFile.copyTo(newPath)
            newPath.path
        }
    }

     suspend fun saveImage2(
        oldName:String,
        path: String,
        examId: Long,
        subjectId: Long
    ): String {
        return withContext(Dispatchers.IO) {

            val oldPath=path(oldName,subjectId, examId)
            println("oldPath ${oldPath.path}")
            oldPath.delete()
            val newPath= newPath(path, subjectId, examId)
            val imageFile=File(path)

            imageFile.copyTo(newPath)

            newPath.name
        }
    }

    fun newPath(path: String,subjectId: Long,examId: Long):File{
        val oldPath=File(path)
        val time=System.currentTimeMillis()
        return path("$time.${oldPath.extension}",subjectId, examId)
        }
    fun path(name:String,subjectId: Long,examId: Long):File{
        val home = File(getGeneraPath(subjectId, examId))
        if (home.exists().not()) {
            home.mkdirs()
        }
        return File(home,name)
    }

    fun getGeneraPath(subjectId: Long,examId: Long):String{
        return "$generalPath/subject/$subjectId/instruction/$examId"
    }

    fun delete(name: String,subjectId: Long,examId: Long){
        path(name, subjectId, examId).delete()
    }


}