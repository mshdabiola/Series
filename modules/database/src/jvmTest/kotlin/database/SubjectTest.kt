package database

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SubjectTest : AbstractTest() {

    @Test
    override fun insert() = runTest {
    }

    override fun delete() {
    }

    override fun getOne() {
    }

    override fun getAll() {
    }
}
