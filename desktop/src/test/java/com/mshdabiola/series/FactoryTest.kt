package com.mshdabiola.series

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Test


class FactoryTest {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun text() = runTest {

        val factory=Factory()

       val qu= factory
            .fileP("""
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

}