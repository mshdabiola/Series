/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.mshdabiola.database.dao.UserDao
import kotlinx.coroutines.Dispatchers
import org.junit.After
import org.junit.Before
import java.io.IOException
import kotlin.test.Test

/**
 * Tests for [NoteDao].
 * This uses an in-memory database for testing.
 *
 * For pure JVM tests without Android framework dependencies (like Context),
 * you might need to adjust the database builder or use a runner like Robolectric
 * if your Room setup (or other dependencies) implicitly requires Android APIs.
 *
 * If `ApplicationProvider.getApplicationContext()` is an issue for pure JVM,
 * you can try `Room.inMemoryDatabaseBuilder(null, AppDatabase::class.java)` if it works,
 * or ensure your testing environment can provide a minimal context (e.g., via Robolectric).
 *
 * For simplicity and common Android testing patterns, RobolectricTestRunner is often used.
 * If you want a pure JVM test without Robolectric, you'd need to ensure your
 * Room.inMemoryDatabaseBuilder doesn't require a Context, or use a different test setup.
 */

class UserDaoTest {

    private lateinit var database: SeriesDatabase
    private lateinit var noteDao: UserDao

    @Before
    fun createDb() {
        database =
            Room
                .inMemoryDatabaseBuilder<SeriesDatabase>()
                .setDriver(BundledSQLiteDriver())
                .setQueryCoroutineContext(Dispatchers.IO)
                .build()
        noteDao = database.getUserDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        database.close()
    }

    @Test
    fun test() {
    }
}
