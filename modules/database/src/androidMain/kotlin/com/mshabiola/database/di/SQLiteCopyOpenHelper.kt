package com.mshabiola.database.di

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import com.mshdabiola.database.BuildConfig
import com.mshdabiola.model.Security
import timber.log.Timber
import java.io.*
import java.util.concurrent.Callable

/**
 * An open helper that will copy & open a pre-populated database if it doesn't exist in internal
 * storage. Only destructive migrations are supported, so it is highly suggested to use this as a
 * read-only database.
 *
 * https://android.googlesource.com/platform/frameworks/support/+/refs/heads/androidx-master-release/room/runtime/src/main/java/androidx/room/SQLiteCopyOpenHelper.java
 */
class SQLiteCopyOpenHelper(
    private val context: Context,
    private val copyConfig: CopyConfig,
    private val databaseVersion: Int,
    private val delegate: SupportSQLiteOpenHelper
) : SupportSQLiteOpenHelper {

    private var verified = false

    private val key = if (BuildConfig.DEBUG)
        BuildConfig.store_key
    else
        BuildConfig.store_key


    override fun setWriteAheadLoggingEnabled(enabled: Boolean) {
        delegate.setWriteAheadLoggingEnabled(enabled)
    }


    override val databaseName: String?
        get() = delegate.databaseName


    override val readableDatabase: SupportSQLiteDatabase
        get() {
            if (!verified) {
                verifyDatabaseFile()
                verified = true
            }
            return delegate.readableDatabase
        }
    override val writableDatabase: SupportSQLiteDatabase
        @Synchronized
        get() {
            if (!verified) {
                verifyDatabaseFile()
                verified = true
            }
            return delegate.writableDatabase
        }

    @Synchronized
    override fun close() {
        delegate.close()
        verified = false
    }

    private fun verifyDatabaseFile() {
        val databaseName = databaseName
        val databaseFile = context.getDatabasePath(databaseName)
        val lockChannel =
            FileOutputStream(File(context.filesDir, "$databaseName.lck")).channel
        try {
            // Acquire a file lock, this lock works across threads and processes, preventing
            // concurrent copy attempts from occurring.
            lockChannel.tryLock()

            if (!databaseFile.exists()) {
                try {
                    // No database file found, copy and be done.
                    copyDatabaseFile(databaseFile)
                    return
                } catch (e: IOException) {
                    throw RuntimeException("Unable to copy database file.", e)
                }
            }

            // A database file is present, check if we need to re-copy it.
            val currentVersion = try {
                readVersion()

            } catch (e: IOException) {
                Timber.tag(TAG).w(e, "Unable to read database version.")
                return
            }
            val oldVersion =
                try {
                    File(databaseFile.parent, "version.txt")
                        .inputStream()
                        .reader()
                        .readText()
                        .toInt()

                } catch (e: IOException) {
                    Timber.tag(TAG).w(e, "Unable to read database version.")
                    0
                }

            Timber.e("current verson is $currentVersion")
            Timber.e("old verson is $oldVersion")


            if (currentVersion == oldVersion) {
                return
            }

            // Always overwrite, we don't support migrations
            if (context.deleteDatabase(databaseName)) {
                try {
                    copyDatabaseFile(databaseFile)
                } catch (e: IOException) {
                    // We are more forgiving copying a database on a destructive migration since
                    // there is already a database file that can be opened.
                    Timber.tag(TAG).w(e, "Unable to copy database file.")
                }
            } else {
                Timber.tag(TAG)
                    .e("Failed to delete database file ($databaseName) for a copy destructive migration.")
            }
        } finally {
            try {
                lockChannel.close()
            } catch (ignored: IOException) {
            }
        }
    }

    /**
     * Reads the user version number out of the database header from the given file.
     *
     * @param databaseFile the database file.
     * @return the database version
     * @throws IOException if something goes wrong reading the file, such as bad database header or
     * missing permissions.
     *
     * @see <a href="https://www.sqlite.org/fileformat.html#user_version_number">User Version
     * Number</a>.
     */
    @Throws(IOException::class)
    private fun readVersion(): Int {
        try {
            return context
                .assets.open("version.txt")
                .reader()
                .readText()
                .toInt()
            //.toIntOrNull() ?: 1
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    @Throws(IOException::class)
    private fun copyDatabaseFile(destinationFile: File) {
        val input = when (copyConfig) {
            is CopyFromAssetPath -> {
                context.assets.open(copyConfig.path)
            }

            is CopyFromFile -> {
                FileInputStream(copyConfig.file)
            }

            is CopyFromInputStream -> {
                copyConfig.callable.call()
            }
        }
        Security.copy(destinationFile,context.assets.open("version.txt"),input,key)
//        val versionOutput = File(destinationFile.parent, "version.txt").outputStream()
//
//        // An intermediate file is used so that we never end up with a half-copied database file
//        // in the internal directory.
//        val intermediateFile = File.createTempFile(
//            "sqlite-copy-helper", ".tmp", context.cacheDir
//        )
//
//
//        intermediateFile.deleteOnExit()
////        input.source().use { a ->
////            intermediateFile.sink().buffer().use { b -> b.writeAll(a) }
////        }
//        versionOutput.use { out ->
//            context.assets.open("version.txt").use {
//                out.write(it.readBytes())
//            }
//        }
//        Security.decode(input, FileOutputStream(intermediateFile), key)
//
//
//        val parent = destinationFile.parentFile
//        if (parent != null && !parent.exists() && !parent.mkdirs()) {
//            throw IOException("Failed to create directories for ${destinationFile.absolutePath}")
//        }
//
//        if (!intermediateFile.renameTo(destinationFile)) {
//            throw IOException("Failed to move intermediate file (${intermediateFile.absolutePath}) to destination (${destinationFile.absolutePath}).")
//        }
    }

    /**
     * Implementation of {@link SupportSQLiteOpenHelper.Factory} that creates
     * {@link SQLiteCopyOpenHelper}.
     */
    class Factory(
        private val context: Context,
        private val copyConfig: CopyConfig,
        private val delegate: SupportSQLiteOpenHelper.Factory
    ) : SupportSQLiteOpenHelper.Factory {
        override fun create(config: SupportSQLiteOpenHelper.Configuration): SupportSQLiteOpenHelper {
            return SQLiteCopyOpenHelper(
                context,
                copyConfig,
                config.callback.version,
                delegate.create(config)
            )
        }
    }

    companion object {
        private const val TAG = "SQLiteCopyOpenHelper"
    }
}

sealed class CopyConfig
data class CopyFromAssetPath(val path: String) : CopyConfig()
data class CopyFromFile(val file: File) : CopyConfig()
data class CopyFromInputStream(val callable: Callable<InputStream>) : CopyConfig()

