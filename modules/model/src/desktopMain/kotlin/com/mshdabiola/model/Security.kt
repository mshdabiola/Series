package com.mshdabiola.model

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.OutputStream
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

object Security {


    fun encode(byteArray: ByteArray, output: OutputStream, key: String) {

        ObjectOutputStream(output).use {
            it.writeObject(encrypt(byteArray, key))
        }

    }

    fun decode(input: InputStream, output: OutputStream, key: String) {


        ObjectInputStream(input).use { objectInputStream ->
            val map: HashMap<String, ByteArray>? =
                objectInputStream.readObject() as? HashMap<String, ByteArray>

            val byte = map?.let { it1 -> decrypt(it1, key) }

            output.use {
                if (byte != null) {
                    it.write(byte)
                }
            }
        }

    }

    fun copy(destinationFile: File, version: InputStream, data: InputStream, key: String) {
        val versionOutput = File(destinationFile.parent, "version.txt").outputStream()

        // An intermediate file is used so that we never end up with a half-copied database file
        // in the internal directory.
        val intermediateFile = File.createTempFile(
            "sqlite-copy-helper", ".tmp"
        )


        intermediateFile.deleteOnExit()
//        input.source().use { a ->
//            intermediateFile.sink().buffer().use { b -> b.writeAll(a) }
//        }
        versionOutput.use { out ->
            version.use {
                out.write(it.readBytes())
            }
        }
        decode(data, FileOutputStream(intermediateFile), key)


        val parent = destinationFile.parentFile
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw IOException("Failed to create directories for ${destinationFile.absolutePath}")
        }

        if (!intermediateFile.renameTo(destinationFile)) {
            throw IOException("Failed to move intermediate file (${intermediateFile.absolutePath}) to destination (${destinationFile.absolutePath}).")
        }
    }

    private fun decrypt(map: HashMap<String, ByteArray>, key: String): ByteArray? {
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

    private fun encrypt(plainTextBytes: ByteArray, key: String): HashMap<String, ByteArray> {
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