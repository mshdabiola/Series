package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.CurrentExam

interface ISettingRepository {

    suspend fun setCurrentExam(currentExam: CurrentExam?)

    suspend fun getCurrentExam(): CurrentExam?
}