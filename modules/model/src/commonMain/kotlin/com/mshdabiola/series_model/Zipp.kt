package com.mshdabiola.series_model

import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.crypto.Cipher
import javax.crypto.CipherInputStream
import javax.crypto.CipherOutputStream
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec

// Zipping function
fun zipDirectory(directory: File, outputStream: OutputStream) {
    outputStream.use { fos ->
        ZipOutputStream(fos).use { zos ->
            directory.walkTopDown().forEach { file ->
                if (file.isFile) {
                    val zipEntry = ZipEntry(directory.toPath().relativize(file.toPath()).toString())
                    zos.putNextEntry(zipEntry)
                    FileInputStream(file).use { fis ->
                        fis.copyTo(zos)
                    }
                    zos.closeEntry()
                }
            }
        }
    }
}

// Unzipping function
fun unzipFile(zipFilePath: String, destDir: String) {
    File(destDir).mkdirs() // Create destination directory if it doesn't exist
    ZipInputStream(FileInputStream(zipFilePath)).use { zis ->
        var entry: ZipEntry? = zis.nextEntry
        while (entry != null) {
            val filePath = destDir + File.separator + entry.name
            if (!entry.isDirectory) {
                // If it's a file, extract it
                extractFile(zis, filePath)
            } else {
                // If it's a directory, create it
                File(filePath).mkdirs()
            }
            zis.closeEntry()
            entry = zis.nextEntry
        }
    }
}

private fun extractFile(zis: ZipInputStream, filePath: String) {
    File(filePath).parentFile.mkdirs()
    FileOutputStream(filePath).use { fos ->
        val buffer = ByteArray(1024)
        var len: Int
        while (zis.read(buffer).also { len = it } > 0) {
            fos.write(buffer, 0, len)
        }
    }
}

// Zipping function with password
fun zipDirectory(directory: File, outputStream: OutputStream, password: String) {
    outputStream.use { fos ->
        val passwordBytes = password.toCharArray()
        val keySpec = PBEKeySpec(
            passwordBytes,
            "salt".toByteArray(),
            65536,
            256,
        ) // Adjust salt and iterations as needed
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
        val secretKey = SecretKeySpec(factory.generateSecret(keySpec).encoded, "AES")
        val cipher = Cipher.getInstance("AES")
        cipher.init(Cipher.ENCRYPT_MODE, secretKey)

        CipherOutputStream(fos, cipher).use { eos ->
            ZipOutputStream(eos).use { zos ->
                directory.walkTopDown().forEach { file ->
                    if (file.isFile) {
                        val zipEntry =
                            ZipEntry(directory.toPath().relativize(file.toPath()).toString())
                        zos.putNextEntry(zipEntry)
                        FileInputStream(file).use { fis ->
                            fis.copyTo(zos)
                        }
                        zos.closeEntry()
                    }
                }
            }
        }
    }
}

// Unzipping function with password
fun unzipFile(inputStream: InputStream, destDir: String, password: String) {
    File(destDir).mkdirs() // Create destination directory if it doesn't exist
    val passwordBytes = password.toCharArray()
    val keySpec = PBEKeySpec(
        passwordBytes,
        "salt".toByteArray(),
        65536,
        256,
    ) // Use the same salt and iterations as during zipping
    val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1")
    val secretKey = SecretKeySpec(factory.generateSecret(keySpec).encoded, "AES")
    val cipher = Cipher.getInstance("AES")
    cipher.init(Cipher.DECRYPT_MODE, secretKey)

    inputStream.use { fis ->
        Cipher.getInstance("AES").let {
            it.init(Cipher.DECRYPT_MODE, secretKey)
            CipherInputStream(fis, it).use { cis ->
                ZipInputStream(cis).use { zis ->
                    var entry: ZipEntry? = zis.nextEntry
                    while (entry != null) {
                        val filePath = destDir + File.separator + entry.name
                        if (!entry.isDirectory) {
                            extractFile(zis, filePath)
                        } else {
                            File(filePath).mkdirs()
                        }
                        zis.closeEntry()
                        entry = zis.nextEntry
                    }
                }
            }
        }
    }
}
