package com.mshdabiola.setting

import com.mshdabiola.model.Contrast
import com.mshdabiola.model.DarkThemeConfig
import com.mshdabiola.model.ThemeBrand
import com.mshdabiola.model.UserData
import com.mshdabiola.model.data.CurrentExam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import kotlinx.coroutines.flow.Flow

interface MultiplatformSettings {

    val name: Flow<String>
    val userData: Flow<UserData>


    suspend fun setName(name: String)


    suspend fun setCurrentInstruction(instruction: Instruction)

    suspend fun getCurrentInstruction(examId: Long): Instruction?

    suspend fun removeInstruction(examId: Long)

    suspend fun setCurrentQuestion(question: QuestionFull)

    suspend fun getCurrentQuestion(examId: Long): QuestionFull?

    suspend fun removeQuestion(examId: Long)

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?


    suspend fun setThemeBrand(themeBrand: ThemeBrand)

    suspend fun setThemeContrast(contrast: Contrast)

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean)

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig)

    suspend fun setShouldHideOnboarding(shouldHideOnboarding: Boolean)
}
