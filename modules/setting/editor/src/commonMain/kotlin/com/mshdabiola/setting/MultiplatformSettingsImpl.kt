package com.mshdabiola.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.setting.model.UserDataSer
import com.mshdabiola.setting.model.toData
import com.mshdabiola.setting.model.toSer
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

internal class MultiplatformSettingsImpl(
    private val settings: DataStore<Preferences>,
    private val coroutineDispatcher: CoroutineDispatcher,
) : MultiplatformSettings {

    val nameKey = stringPreferencesKey("Name")
    val instructionKey = stringPreferencesKey("instruction")
    val questionKey = stringPreferencesKey("question")
    val currentExamKey = stringPreferencesKey("currentExam")
    private val userDataKey = stringPreferencesKey("UserData")

    override val name: Flow<String> = settings.data.map { it[nameKey] ?: "nothing" }
    override val userData: Flow<UserData>
        get() = settings.data.map {
            val userData = it[userDataKey]
            if (userData != null) {
                Json.decodeFromString<UserDataSer>(userData).toData()
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

    override suspend fun setName(name: String) {
        settings.edit { it[nameKey] = name }
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

            settings.edit {
                val crString = Json.encodeToString(ListSerializer(Instruction.serializer()), list)
                it[instructionKey] = crString
            }
//            settings
//                .toBlockingSettings()
//                .encodeValue(ListSerializer(Instruction.serializer()), Keys.instructionKey, list)
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
            settings.edit {
                val crString = Json.encodeToString(ListSerializer(Instruction.serializer()), list)
                it[instructionKey] = crString
            }
//            settings
//                .toBlockingSettings()
//                .encodeValue(ListSerializer(Instruction.serializer()), Keys.instructionKey, list)
        }
    }

    private suspend fun getInstructionList(): List<Instruction> {
        val data = settings.data.map {
            val instrus = it[instructionKey]
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
            settings.edit {
                val crString = Json.encodeToString(ListSerializer(QuestionFull.serializer()), list)
                it[questionKey] = crString
            }
//            settings
//                .toBlockingSettings()
//                .encodeValue(ListSerializer(QuestionFull.serializer()), Keys.questionKey, list)
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
            settings.edit {
                val crString = Json.encodeToString(ListSerializer(QuestionFull.serializer()), list)
                it[questionKey] = crString
            }
//            settings
//                .toBlockingSettings()
//                .encodeValue(ListSerializer(QuestionFull.serializer()), Keys.questionKey, list)
        }
    }

    override suspend fun setCurrentExam(currentExam: CurrentExam?) {
        withContext(Dispatchers.IO) {
            settings.edit {
                val crString = Json.encodeToString(
                    ListSerializer(CurrentExam.serializer()),
                    if (currentExam == null) emptyList() else listOf(currentExam),
                )
                it[currentExamKey] = crString
            }
//            settings
//                .toBlockingSettings()
//                .encodeValue(
//                    ListSerializer(CurrentExam.serializer()), Keys.currentExamKey,
//                    if (currentExam == null) emptyList() else listOf(currentExam)
//                )
        }
    }

    override suspend fun getCurrentExam(): CurrentExam? {
        val data = settings.data.map {
            val current = it[currentExamKey]
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
        val data = settings.data.map {
            val questionFull = it[questionKey]
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
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setThemeContrast(contrast: Contrast) {
        val userData = userData.first().copy(contrast = contrast)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        val userData = userData.first().copy(useDynamicColor = useDynamicColor)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        val userData = userData.first().copy(darkThemeConfig = darkThemeConfig)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }

    override suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean) {
        val userData = userData.first().copy(shouldHideOnboarding = shouldHideOnboarding)
        val userDataStr = Json.encodeToString(userData.toSer())
        settings.edit { it[userDataKey] = userDataStr }
    }
}
