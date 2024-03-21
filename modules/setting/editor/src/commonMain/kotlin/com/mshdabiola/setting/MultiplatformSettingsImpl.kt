package com.mshdabiola.setting

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.UserDataSer
import com.mshdabiola.model.data.toData
import com.mshdabiola.model.data.toSer
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.FlowSettings
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@OptIn(ExperimentalSettingsApi::class)
internal class MultiplatformSettingsImpl(
    private val settings: FlowSettings,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {
    private val nameKey="name"
    private val dummyKey="dummy"
    private val userKey="userKey"


    val instructionKey = "instruction"
    val questionKey = "question"
    val currentExamKey = "currentExam"

    override val userData: Flow<UserData>
        get() = settings.getStringOrNullFlow(userKey) .map {

            if (it != null) {
                Json.decodeFromString<UserDataSer>(it).toData()
            } else {
                UserData(
                    themeBrand = ThemeBrand.DEFAULT,
                    darkThemeConfig = DarkThemeConfig.LIGHT,
                    useDynamicColor = false,
                    shouldHideOnboarding = false,
                    contrast = Contrast.Normal,
                )
            }
        }



    override suspend fun setCurrentInstruction(instruction: Instruction) {
        withContext(Dispatchers.IO) {
            val list = getInstructionList().toMutableList()
            val index = list.indexOfFirst { it.examId == instruction.examId }
            if (index == -1) {
                list.add(instruction)
            } else {
                list[index] = instruction
            }
            val crString = Json.encodeToString(ListSerializer(Instruction.serializer()), list)

            settings.putString(instructionKey,crString)
        }
    }

    override suspend fun getCurrentInstruction(examId: Long): Instruction? {
        return getInstructionList()
            .find { it.examId == examId }
    }

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
            val crString = Json.encodeToString(ListSerializer(Instruction.serializer()), list)

            settings.putString(instructionKey,crString)
        }
    }

    private suspend fun getInstructionList(): List<Instruction> {
        val data = settings.getStringOrNullFlow(instructionKey).map {instrus->
            if (instrus == null) {
                emptyList<Instruction>()
            } else {
                Json.decodeFromString(ListSerializer(Instruction.serializer()), instrus)
            }
        }
        return data.first()
    }

    override suspend fun setCurrentQuestion(question: QuestionFull) {
        withContext(Dispatchers.IO) {
            val list = getQuestionList().toMutableList()
            val index = list.indexOfFirst { it.examId == question.examId }
            if (index == -1) {
                list.add(question)
            } else {
                list[index] = question
            }
            val crString = Json.encodeToString(ListSerializer(QuestionFull.serializer()), list)

            settings.putString(questionKey,crString)
        }
    }

    override suspend fun getCurrentQuestion(examId: Long): QuestionFull? {
        return getQuestionList()
            .find { it.examId == examId }
    }

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
            val crString = Json.encodeToString(ListSerializer(QuestionFull.serializer()), list)

            settings.putString(questionKey,crString)
        }
    }

    override suspend fun setCurrentExam(currentExam: CurrentExam?) {
        withContext(Dispatchers.IO) {
            val crString = Json.encodeToString(
                ListSerializer(CurrentExam.serializer()),
                if (currentExam == null) emptyList() else listOf(currentExam),
            )
            settings.putString(currentExamKey,crString)
        }
    }

    override suspend fun getCurrentExam(): CurrentExam? {
        val data = settings.getStringOrNullFlow(currentExamKey).map {current->
            if (current == null) {
                null
            } else {
                Json.decodeFromString(ListSerializer(CurrentExam.serializer()), current)
            }
        }
        return data.firstOrNull()?.firstOrNull()
//        val list = settings
//            .toBlockingSettings()
//            .decodeValueOrNull(ListSerializer(CurrentExam.serializer()), Keys.currentExamKey)
//        return list?.firstOrNull()
    }

    private suspend fun getQuestionList(): List<QuestionFull> {
        val data = settings.getStringOrNullFlow(questionKey).map {questionFull->
            if (questionFull == null) {
                emptyList()
            } else {
                Json.decodeFromString(ListSerializer(QuestionFull.serializer()), questionFull)
            }
        }
        return data.first()
//        return settings.toBlockingSettings().decodeValue(
//            ListSerializer(QuestionFull.serializer()),
//            key = Keys.questionKey,
//            emptyList()
//        )
    }
    override suspend fun setThemeBrand(themeBrand: ThemeBrand) {
        val userData = userData.first().copy(themeBrand = themeBrand)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setThemeContrast(contrast: Contrast) {
        val userData = userData.first().copy(contrast = contrast)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        val userData = userData.first().copy(useDynamicColor = useDynamicColor)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        val userData = userData.first().copy(darkThemeConfig = darkThemeConfig)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.putString(userKey,userDataStr)
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        val userData = userData.first().copy(shouldHideOnboarding = shouldHideOnboarding)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.putString(userKey,userDataStr)
    }
}
