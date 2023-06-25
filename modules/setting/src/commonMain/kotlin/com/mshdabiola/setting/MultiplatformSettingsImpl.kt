package com.mshdabiola.setting

import com.mshdabiola.model.CurrentExam
import com.mshdabiola.model.DummySetting
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.setting.model.Dummy
import com.mshdabiola.setting.model.toDummy
import com.mshdabiola.setting.model.toDummySetting
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import com.russhwolf.settings.coroutines.toBlockingSettings
import com.russhwolf.settings.serialization.decodeValue
import com.russhwolf.settings.serialization.decodeValueOrNull
import com.russhwolf.settings.serialization.encodeValue
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.builtins.ListSerializer

@OptIn(ExperimentalSettingsApi::class)
internal class MultiplatformSettingsImpl(
    private val settings: FlowSettings,
    private val coroutineDispatcher: CoroutineDispatcher
) : MultiplatformSettings {


    override val name = settings.getStringFlow("NAME", "Jamiu")

    private val _dummy = MutableStateFlow(
        settings.toBlockingSettings().decodeValue(
            Dummy.serializer(), key = Keys.setting, defaultValue = Dummy(
                name = "Serena",
                sex = "Lyle"
            )
        ).toDummySetting()
    )

    @OptIn(ExperimentalSerializationApi::class)
    override val dummy: StateFlow<DummySetting>
        get() = _dummy.asStateFlow()

    override suspend fun setName(name: String) {
        settings.putString("NAME", name)
    }


    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun setDummy(dummy: DummySetting) {
        _dummy.update {
            dummy
        }
// Store values for the properties of someClass in settings
        settings.toBlockingSettings().encodeValue(Dummy.serializer(), Keys.setting, dummy.toDummy())

        val dummy2 = settings.toBlockingSettings().decodeValue(
            Dummy.serializer(), key = Keys.setting, defaultValue = Dummy(
                name = "Serena",
                sex = "Lyle"
            )
        )

        println("dummy $dummy")
        println("dummy2 $dummy2")
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun setCurrentInstruction(instruction: Instruction) {
        withContext(Dispatchers.IO) {

            val list = getInstructionList().toMutableList()
            val index = list.indexOfFirst { it.examId == instruction.examId }
            if (index == -1) {
                list.add(instruction)
            } else {
                list[index] = instruction
            }
            settings
                .toBlockingSettings()
                .encodeValue(ListSerializer(Instruction.serializer()), Keys.instructionKey, list)

        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getCurrentInstruction(examId: Long): Instruction? {
        return getInstructionList()
            .find { it.examId == examId }

    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun removeInstruction(examId: Long) {
        withContext(Dispatchers.IO) {

            val list = getInstructionList().toMutableList()
            val index = list.indexOfFirst { it.examId == examId }
            if (index == -1) {
                return@withContext
            } else {
                list.removeAt(index)
                println("remove")
            }
            settings
                .toBlockingSettings()
                .encodeValue(ListSerializer(Instruction.serializer()), Keys.instructionKey, list)

        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getInstructionList(): List<Instruction> {
        return settings.toBlockingSettings().decodeValue(
            ListSerializer(Instruction.serializer()),
            key = Keys.instructionKey,
            emptyList()
        )
    }


    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun setCurrentQuestion(question: QuestionFull) {
        withContext(Dispatchers.IO) {

            val list = getQuestionList().toMutableList()
            val index = list.indexOfFirst { it.examId == question.examId }
            if (index == -1) {
                list.add(question)
            } else {
                list[index] = question
            }
            settings
                .toBlockingSettings()
                .encodeValue(ListSerializer(QuestionFull.serializer()), Keys.questionKey, list)

        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override fun getCurrentQuestion(examId: Long): QuestionFull? {
        return getQuestionList()
            .find { it.examId == examId }

    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun removeQuestion(examId: Long) {
        withContext(Dispatchers.IO) {

            val list = getQuestionList().toMutableList()
            val index = list.indexOfFirst { it.examId == examId }
            if (index == -1) {
                return@withContext
            } else {
                list.removeAt(index)
                println("remove")
            }
            settings
                .toBlockingSettings()
                .encodeValue(ListSerializer(QuestionFull.serializer()), Keys.questionKey, list)

        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun setCurrentExam(currentExam: CurrentExam) {
        withContext(Dispatchers.IO){
            settings
                .toBlockingSettings()
                .encodeValue(CurrentExam.serializer(),Keys.currentExamKey,currentExam)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getCurrentExam(): CurrentExam? {
      return settings
          .toBlockingSettings()
          .decodeValueOrNull(CurrentExam.serializer(),Keys.currentExamKey)
    }

    @OptIn(ExperimentalSerializationApi::class)
    private fun getQuestionList(): List<QuestionFull> {
        return settings.toBlockingSettings().decodeValue(
            ListSerializer(QuestionFull.serializer()),
            key = Keys.questionKey,
            emptyList()
        )
    }

}
