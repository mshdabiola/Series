package com.mshabiola.data

import app.cash.turbine.test
import com.mshdabiola.data.repository.Generative
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.onErrorResume
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.io.File
import kotlin.time.Duration
import kotlin.time.DurationUnit
import kotlin.time.toDuration

class GenerativeTest {

    @Test
    fun generateContent()= runTest {
        Generative()
            .generateContent("who are you")
            .test (2.toDuration(DurationUnit.MINUTES)){
                println(awaitItem().text)
                awaitComplete()
            }
    }

    @Test
    fun testGenerateContent()= runTest {
        val image="/home/mshdabiola/Downloads/IMG_0481.JPG"
        val generative=Generative()

        generative
            .generateContent("convert the image to questions",File(image).readBytes())
            .catch {
                it.printStackTrace()
            }
            .collectLatest {
                println(it.text)
            }

    }
}