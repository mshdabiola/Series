package com.mshabiola

import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec


class ExInPort(
    //private val fileManager: FileManager
) {



//    suspend fun copyImage(dir: String, subject: List<Subject>) {
//        withContext(Dispatchers.IO) {
//            try {
//                subject.forEach {
//                    val from = File(FileManager.getSubjectPath(it.id))
//                    val to = File(dir, it.name)
//                    if (to.exists().not()) {
//                        to.mkdirs()
//                    }
//
//                    from.copyRecursively(to, true)
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//
//        }
//    }


    companion object {
        val exam = "exam"
        val instruction = "instruct"
        val option = "options"
        val question = "question"
        val subject = "subject"
        val topic = "topic"
    }
}