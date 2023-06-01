package com.mshdabiola.series

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test


class FactoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun text() = runTest {

        val factory=Factory()

       val qu= factory
            .textToQuestion("""
                q*sss
                *o*sss
                *o*sfs
                *o*ddkd
                *q*What is your name
                *o*abiola
                *o*moshood
                *o*lawal
            """.trimIndent(),67,78)
        println(qu)

    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun text2() = runTest {

        val factory=Factory()

        val qu= factory
            .textToInstruction("""
                *aksks
                *sjsj
                *sjsj
            """.trimIndent(),44)
        println(qu)

    }

}