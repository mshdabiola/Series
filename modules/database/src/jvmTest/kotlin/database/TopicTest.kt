package database

import com.mshdabiola.seriesdatabase.dao.TopicDao
import org.junit.Test
import org.koin.core.component.inject

class TopicTest : AbstractTest() {

    val topicDao by inject<TopicDao>()

    @Test
    override fun insert() {
        TODO("Not yet implemented")
    }

    @Test
    override fun delete() {
        TODO("Not yet implemented")
    }

    @Test
    override fun getOne() {
        TODO("Not yet implemented")
    }

    @Test
    override fun getAll() {
        TODO("Not yet implemented")
    }
}
