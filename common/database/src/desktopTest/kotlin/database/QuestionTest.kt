package database

import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.data.Item
import org.junit.Test
import kotlin.test.assertEquals

class QuestionTest :AbstractTest() {
    @Test
    fun text(){
        val qvalue= listOf(Item("abiola"),Item("Moshood"))
        val str= listOfValueAdapter.encode(qvalue)

        println(str)
        val qlist=listOfValueAdapter.decode(str)
        println(qlist)

        assertEquals(qvalue,qlist)
    }
    override fun insert() {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun getOne() {
        TODO("Not yet implemented")
    }

    override fun getAll() {
        TODO("Not yet implemented")
    }

}