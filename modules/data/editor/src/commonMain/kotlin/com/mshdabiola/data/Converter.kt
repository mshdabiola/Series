package com.mshdabiola.data

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
        subjectId: Long,
    ): List<Topic> {
        return withContext(Dispatchers.IO) {
            path
                .split(Regex("\\s*\\*\\s*"))
                .filter { it.isNotBlank() }
                .map {
                    Topic(
                        subjectId = subjectId,
                        name = it,
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
                        content = listOf(itemise(it)),
                    )
                }
        }
    }

    suspend fun textToQuestion(
        path: String,
        examId: Long,
        nextObjNumber: Long,
        nextTheoryNumber: Long,
    ): List<QuestionFull> {
        return withContext(Dispatchers.IO) {
            val list = mutableListOf<QuestionFull>()
            var options = mutableListOf<String>()
            var answer: String? = null
            var isTheory = false
            var objNo = nextObjNumber
            var thrNo = nextTheoryNumber
            var question: String? = null
            val s = path
                .split(Regex("\\s*\\*\\s*"))
                .toMutableList()
            println(s.joinToString())
            s.removeAt(0)

            s.chunked(2) {
                Pair(it[0].trim(), it[1])
            }
                .onEach { println(it) }
                .forEachIndexed { _, pair ->
                    when (pair.first) {
                        "q" -> {
                            if (question != null) {
                                if (isTheory) {
                                    list.add(
                                        convertThe(
                                            theoryNo = thrNo,
                                            content = question!!,
                                            examId = examId,
                                            answer = answer ?: "",
                                        ),
                                    )

                                    thrNo += 1
                                } else {
                                    list.add(
                                        convertObj(
                                            questionNos = objNo,
                                            content = question!!,
                                            examId = examId,
                                            options = options,
                                        ),
                                    )
                                    objNo += 1
                                }
                                isTheory = false
                                answer = null
                                question = null
                                options = mutableListOf()
                            }
                            question = pair.second
                        }

                        "o" -> {
                            options.add(pair.second)
                        }

                        "t" -> {
                            isTheory = (pair.second.toIntOrNull() ?: 0) == 1
                        }

                        "a" -> {
                            answer = pair.second
                        }
                    }
                }
            if (question != null) {
                if (isTheory) {
                    list.add(
                        convertThe(
                            theoryNo = thrNo,
                            content = question!!,
                            examId = examId,
                            answer = answer ?: "",
                        ),
                    )
                } else {
                    list.add(
                        convertObj(
                            questionNos = objNo,
                            content = question!!,
                            examId = examId,
                            options = options,
                        ),
                    )
                }
            }

            list
        }
    }

    private fun convertObj(
        questionNos: Long,
        content: String,
        examId: Long,
        options: List<String>,
    ): QuestionFull {
        val opti = options
            .mapIndexed { index, s ->
                Option(
                    nos = index + 1L,
                    questionId = questionNos,
                    examId = examId,
                    content = listOf(itemise(s)),
                    isAnswer = false,
                )
            }

        return QuestionFull(
            nos = questionNos,
            examId = examId,
            content = listOf(itemise(content)),
            options = opti,
            isTheory = opti.isEmpty(),
            answer = null,
            instruction = null,
            topic = null,
        )
    }

    private fun convertThe(
        theoryNo: Long,
        content: String,
        examId: Long,
        answer: String,
    ): QuestionFull {
        return QuestionFull(
            nos = theoryNo,
            examId = examId,
            content = listOf(itemise(content)),
            options = emptyList(),
            isTheory = true,
            answer = listOf(itemise(answer)),
            instruction = null,
            topic = null,
        )
    }

    private fun itemise(string: String): Item {
        return Item(content = string)
    }
}
