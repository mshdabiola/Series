package com.mshabiola.database.model

import app.cash.sqldelight.ColumnAdapter
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Type

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
                        Item(it.first, Type.valueOf(it.second))
                    }



    }

    override fun encode(value: List<Item>): String {
        return value
                .joinToString(separator = delimit) {
                   "${it.content}$delimit${it.type}"

                }

    }

}