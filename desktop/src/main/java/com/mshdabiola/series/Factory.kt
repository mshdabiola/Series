package com.mshdabiola.series

import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Factory() {

    suspend fun fileP(
        path: String,
        examId: Long,
        nextQuestionNos: Long
    ): List<QuestionFull> {
        return withContext(Dispatchers.IO) {

            val list = mutableListOf<QuestionFull>()
            var options = mutableListOf<String>()
            var nos = nextQuestionNos
            var question: String? = null
            path
                .split(Regex("\\s*\\*\\s*"))
                .chunked(2) {
                    Pair(it[0].trim(), it[1])
                }
                .onEach { println(it) }
                .forEachIndexed { index, pair ->
                    when (pair.first) {
                        "q" -> {
                            if (question != null) {
                                list.add(
                                    convert(
                                        questionNos = nos,
                                        content = question!!,
                                        examId = examId,
                                        options = options
                                    )
                                )
                                nos += 1
                                question = null
                                options = mutableListOf()
                            }
                            question = pair.second

                        }

                        "o" -> {
                            options.add(pair.second)
                        }
                    }
                }
            if (question != null) {
                list.add(
                    convert(
                        questionNos = nos,
                        content = question!!,
                        examId = examId,
                        options = options
                    )
                )
                question = null
                options = mutableListOf()
            }

            list
        }
    }


    private fun convert(
        questionNos: Long,
        content: String,
        examId: Long,
        options: List<String>
    ): QuestionFull {
        val opti = options
            .mapIndexed { index, s ->
                Option(
                    nos = index + 1L,
                    questionNos = questionNos,
                    examId = examId,
                    content = listOf(itemise(s)),
                    isAnswer = false
                )
            }

        return QuestionFull(
            nos = questionNos,
            examId = examId,
            content = listOf(itemise(content)),
            options = opti,
            isTheory = false,
            answer = null,
            instruction = null,
            topic = null
        )

    }

    private fun itemise(string: String): Item {
        return Item(content = string)
    }


}
