package com.mshabiola.database.di

import android.content.Context
import android.util.Log
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import com.mshabiola.database.util.Constant
import okio.IOException
import okio.buffer
import okio.sink
import okio.source
import timber.log.Timber
import java.io.*
import java.nio.ByteBuffer
import java.util.concurrent.Callable
import kotlin.jvm.Throws

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
                readVersion(context.assets.open(Constant.databaseName))
            } catch (e: IOException) {
                Timber.tag(TAG).w(e, "Unable to read database version.")
                return
            }

            if (currentVersion == databaseVersion) {
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
                    .w("Failed to delete database file ($databaseName) for a copy destructive migration.")
            }
        } finally {
            try {
                lockChannel.close()
            } catch (ignored: IOException) {}
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
    private fun readVersion(inputStream: InputStream): Int {
        try {

            val header = ByteArray(100)
            inputStream.read(header)
            inputStream.close()


            // The user version is stored at offset 60.
            val x1=header[60].toInt()
            val x2= header[61].toInt()
            val x3= header[62].toInt()
            val x4=header[63].toInt()
            return "$x1$x2$x3$x4".toInt()
        } catch (e: java.io.IOException) {
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

        // An intermediate file is used so that we never end up with a half-copied database file
        // in the internal directory.
        val intermediateFile = File.createTempFile(
            "sqlite-copy-helper", ".tmp", context.cacheDir
        )
        intermediateFile.deleteOnExit()
        input.source().use { a ->
            intermediateFile.sink().buffer().use { b -> b.writeAll(a) }
        }

        val parent = destinationFile.parentFile
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw IOException("Failed to create directories for ${destinationFile.absolutePath}")
        }

        if (!intermediateFile.renameTo(destinationFile)) {
            throw IOException("Failed to move intermediate file (${intermediateFile.absolutePath}) to destination (${destinationFile.absolutePath}).")
        }
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
data class CopyFromAssetPath(val path: String): CopyConfig()
data class CopyFromFile(val file: File): CopyConfig()
data class CopyFromInputStream(val callable: Callable<InputStream>): CopyConfig()

