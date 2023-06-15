package com.mshdabiola.util

import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class Converter {


    suspend fun textToTopic(
        path: String,
        subjectId: Long
    ): List<Topic> {
        return withContext(Dispatchers.IO) {
            path
                .split(Regex("\\s*\\*\\s*"))
                .filter { it.isNotBlank() }
                .map {
                    Topic(
                        subjectId = subjectId,
                        name = it
                    )
                }

        }
    }


    suspend fun textToInstruction(
        path: String,
        examId: Long,
    ): List<Instruction> {
        return withContext(Dispatchers.IO) {
            path
                .split(Regex("\\s*\\*\\s*"))

                .filter { it.isNotBlank() }
                .map {
                    Instruction(
                        examId = examId,
                        title = null,
                        content = listOf(itemise(it))
                    )
                }
        }
    }

    suspend fun textToQuestion(
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
