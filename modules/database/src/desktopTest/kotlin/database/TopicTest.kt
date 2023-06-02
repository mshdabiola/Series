package database

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class TopicTest : AbstractTest() {

    override fun insert() = runTest {


    }

    override fun delete() = runTest {
        TODO("Not yet implemented")
    }

    override fun getOne() = runTest {
        TODO("Not yet implemented")
    }

    override fun getAll() = runTest {
        TODO("Not yet implemented")
    }

}