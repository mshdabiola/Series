package com.mshdabiola.setting

import com.mshdabiola.setting.model.Dummy

object Keys {
    const val questionKey = "questionList"
    const val setting = "dummy"
    const val instructionKey = "instructionlist"
    const val currentExamKey = "currentExam"


    object Defaults {
        val defaultDummy = Dummy("abiola", "male")
    }
}
