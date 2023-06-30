package com.mshdabiola.util

import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import kotlinx.serialization.json.encodeToStream
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


@OptIn(ExperimentalSerializationApi::class)
class ExInPort(
    private val fileManager: FileManager
) {
    private val passwordString = "Swordfish"

    suspend inline fun <reified T> export(
        exams: List<T>,
        dir: String,
        name: String
    ) {
        withContext(Dispatchers.IO) {
            val file = File(dir, "$name.ex")
            val byteArrayOutputStream = ByteArrayOutputStream()
            Json.encodeToStream(exams, byteArrayOutputStream)
            writeIt(byteArrayOutputStream.toByteArray(), file)

        }

    }

    suspend inline fun <reified T> import(
        dir: String,
        name: String
    ): List<T> {
        return withContext(Dispatchers.IO) {
            val file = File(dir, "$name.ex")
            Json.decodeFromStream(ByteArrayInputStream(readIt(file)))
        }
    }

    suspend inline fun <reified T> import(
       inputStream: InputStream
    ): List<T> {
        return withContext(Dispatchers.IO) {
            Json.decodeFromStream(ByteArrayInputStream(readIt(inputStream)))
        }
    }

    suspend fun copyImage(dir: String, subject: List<Subject>) {
        withContext(Dispatchers.IO) {
            subject.forEach {
                val from = File(fileManager.getSubjectPath(it.id))
                val to = File(dir, it.name)
                if (to.exists().not()) {
                    to.mkdirs()
                }

                from.copyRecursively(to, true)
            }

        }
    }


    fun writeIt(byteArray: ByteArray, file: File) {
        if (file.exists().not()) {
            if (file.parentFile.exists().not()) {
                file.parentFile.mkdirs()
            }

            file.createNewFile()
        }
        val fileOutputStream = FileOutputStream(file)
        ObjectOutputStream(fileOutputStream).use {
            it.writeObject(encrypt(byteArray))
        }

    }

    fun readIt(file: File): ByteArray? {

        val fileInputStream = FileInputStream(file)
        return ObjectInputStream(fileInputStream).use {
            val map: HashMap<String, ByteArray>? = it.readObject() as? HashMap<String, ByteArray>

            map?.let { it1 -> decrypt(it1) }
        }

    }

    fun readIt(inputStream : InputStream): ByteArray? {

        val fileInputStream =inputStream
        return ObjectInputStream(fileInputStream).use {
            val map: HashMap<String, ByteArray>? = it.readObject() as? HashMap<String, ByteArray>

            map?.let { it1 -> decrypt(it1) }
        }

    }


    private fun decrypt(map: HashMap<String, ByteArray>): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            // 1 regenerate key from password
            val passwordChar = passwordString.toCharArray()
            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256)
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            val keySpec = SecretKeySpec(keyBytes, "AES")

            // 2 Decrypt
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            val ivSpec = IvParameterSpec(iv)
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
            decrypted = cipher.doFinal(encrypted)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        // 3
        return decrypted
    }

    private fun encrypt(plainTextBytes: ByteArray): HashMap<String, ByteArray> {
        val map = HashMap<String, ByteArray>()

        try {
            //Random salt for next step
            // 1
            val random = SecureRandom()
            // 2
            val salt = ByteArray(256)
            // 3
            random.nextBytes(salt)

            //PBKDF2 - derive the key from the password, don't use passwords directly
            // 4
            val passwordChar = passwordString.toCharArray() //Turn password into char[] array
            // 5
            val pbKeySpec = PBEKeySpec(passwordChar, salt, 1324, 256) //1324 iterations
            // 6
            val secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
            // 7
            val keyBytes = secretKeyFactory.generateSecret(pbKeySpec).encoded
            // 8
            val keySpec = SecretKeySpec(keyBytes, "AES")

            //Create initialization vector for AES
            // 9
            val ivRandom = SecureRandom() //not caching previous seeded instance of SecureRandom
            // 10
            val iv = ByteArray(16)
            // 11
            ivRandom.nextBytes(iv)
            // 12
            val ivSpec = IvParameterSpec(iv)

            //Encrypt
            // 13
            val cipher = Cipher.getInstance("AES/CBC/PKCS5Padding")
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
            // 14
            val encrypted = cipher.doFinal(plainTextBytes)
            // 15
            map["salt"] = salt
            map["iv"] = iv
            map["encrypted"] = encrypted
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return map
    }

    companion object {
        val exam = "exam"
        val instruction = "instruct"
        val option = "options"
        val question = "question"
        val subject = "subject"
        val topic = "topic"
    }
}