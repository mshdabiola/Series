package database

import com.mshdabiola.seriesdatabase.dao.TopicDao
import org.junit.Test
import org.koin.core.component.inject

class TopicTest : AbstractTest() {

    val topicDao by inject<TopicDao>()

    @Test
    override fun insert() {
    }

    @Test
    override fun delete() {
    }

    @Test
    override fun getOne() {
    }

    @Test
    override fun getAll() {
    }
}
