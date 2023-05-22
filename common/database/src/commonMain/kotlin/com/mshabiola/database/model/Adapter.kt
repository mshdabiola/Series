package com.mshabiola.database.model

import app.cash.sqldelight.ColumnAdapter
import com.mshdabiola.model.Item

val listOfStringAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String): List<String> {
        TODO("Not yet implemented")
    }

    override fun encode(value: List<String>): String {

        TODO("Not yet implemented")
    }


}


val listOfValueAdapter = object : ColumnAdapter<List<Item>, String> {
    private val delimit = "||"
    override fun decode(databaseValue: String): List<Item> {
        val list = databaseValue
            .split(delimit)
            .chunked(2) {
                Pair(it[0], it[1])
            }

        return list
                    .map {
                        when (it.first) {
                            "1" -> Item.Text(it.second)
                            "2" -> Item.Equation(it.second)
                            "3" -> Item.Image(it.second)
                            else -> Item.Text(it.second)
                        }
                    }



    }

    override fun encode(value: List<Item>): String {
        return value
                .joinToString(separator = delimit) {
                    when (it) {
                        is Item.Text -> "1$delimit${it.tex}"
                        is Item.Equation -> "2$delimit${it.tex}"
                        is Item.Image -> "3$delimit${it.imageName}"
                    }

                }

    }

}