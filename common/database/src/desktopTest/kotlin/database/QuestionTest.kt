package database

import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.model.ItemUi
import org.junit.Test
import kotlin.test.assertEquals

class QuestionTest :AbstractTest() {
    @Test
    fun text(){
        val qvalue= listOf(ItemUi.Text("abiola"),ItemUi.Equation("Moshood"))
        val str= listOfValueAdapter.encode(qvalue)

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