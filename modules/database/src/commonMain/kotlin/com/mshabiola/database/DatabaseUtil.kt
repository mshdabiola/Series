package com.mshabiola.database

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.security.SecureRandom
import java.util.Properties
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import kotlin.io.path.Path
import kotlin.io.path.inputStream
import kotlin.io.path.outputStream
import kotlin.io.path.pathString
import kotlin.io.path.readBytes

object DatabaseUtil {

    //private const val passwordString = "Swordfish"

    suspend fun import(
        output : SeriesDatabase,
        path :String,
        key:String
    ){
        withContext(Dispatchers.IO){
            val dbPath = Path(path)
            decode(dbPath.inputStream(),dbPath.outputStream(),key)



            val driver = JdbcSqliteDriver(
                "jdbc:sqlite:${dbPath.pathString}",
                properties = Properties().apply { put("foreign_keys", "true") }
            )
                .also { SeriesDatabase.Schema.create(it) }
            val input=  SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )

            val exams = input.examQueries
                .getAll()
                .executeAsList()

            val subject=input.subjectQueries
                .getAll()
                .executeAsList()

            val questions =input.questionQueries
                .getAll()
                .executeAsList()
            val options=input.optionQueries
                .getAll()
                .executeAsList()

            val instructions=input.instructionQueries
                .getAll()
                .executeAsList()

            val topics=input.topicQueries
                .getAll()
                .executeAsList()

            subject.forEach {
                output.subjectQueries.insertOrReplace(it)
            }

            exams.forEach {
                output.examQueries.insertOrReplace(it)
            }

            questions.forEach {
                output.questionQueries.insertReplace(it)
            }

            options.forEach {
                output.optionQueries.insertOrReplace(it)
            }

            topics.forEach {
                output.topicQueries.insertOrReplace(it)
            }

            instructions.forEach {
                output.instructionQueries.insertOrReplace(it)
            }

            driver.close()
        }

    }


    suspend fun export(
        inputDb : SeriesDatabase,
        examsId : List<Long>,
        path :String,
        version:Int,
        key:String
    ){
        withContext(Dispatchers.IO){
             val dbPath = Path(path)

            val driver = JdbcSqliteDriver(
                "jdbc:sqlite:${dbPath.pathString}",
                properties = Properties().apply { put("foreign_keys", "true") }
            )
                .also { SeriesDatabase.Schema.create(it) }
            driver.execute(null, "PRAGMA user_version=$version", 0)

            val db=  SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )

            val exams = inputDb.examQueries
                .getByIds(examsId)
                .executeAsList()

            val subject=inputDb.subjectQueries
                .getByIds(exams.map { it.subjectId })
                .executeAsList()

            val questions =inputDb.questionQueries
                .getByIds(examsId)
                .executeAsList()
            val options=inputDb.optionQueries
                .getByIds(examsId)
                .executeAsList()

            val instructions=inputDb.instructionQueries
                .getByIds(questions.mapNotNull { it.instructionId}.distinct())
                .executeAsList()

            val topics=inputDb.topicQueries
                .getByIds(questions.mapNotNull { it.topicId}.distinct())
                .executeAsList()

            subject.forEach {
                db.subjectQueries.insertOrReplace(it)
            }

            exams.forEach {
                db.examQueries.insertOrReplace(it)
            }

            questions.forEach {
                db.questionQueries.insertReplace(it)
            }

            options.forEach {
                db.optionQueries.insertOrReplace(it)
            }

            topics.forEach {
                db.topicQueries.insertOrReplace(it)
            }

            instructions.forEach {
                db.instructionQueries.insertOrReplace(it)
            }

                driver.close()

            encode(dbPath.readBytes(),dbPath.outputStream(),key)



            }

        }


    private fun encode(byteArray: ByteArray, output: OutputStream,key:String) {

        ObjectOutputStream(output).use {
            it.writeObject(encrypt(byteArray,key))
        }

    }

     fun decode(input:InputStream, output: OutputStream,key:String){


         ObjectInputStream(input).use { objectInputStream ->
             val map: HashMap<String, ByteArray>? = objectInputStream.readObject() as? HashMap<String, ByteArray>

          val byte=  map?.let { it1 -> decrypt(it1,key) }

             output.use {
                 if (byte != null) {
                     it.write(byte)
                 }
             }
        }

    }

    private fun decrypt(map: HashMap<String, ByteArray>,key:String): ByteArray? {
        var decrypted: ByteArray? = null
        try {
            val salt = map["salt"]
            val iv = map["iv"]
            val encrypted = map["encrypted"]

            // 1 regenerate key from password
            val passwordChar = key.toCharArray()
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

    private fun encrypt(plainTextBytes: ByteArray,key:String): HashMap<String, ByteArray> {
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
            val passwordChar = key.toCharArray() //Turn password into char[] array
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


    }

