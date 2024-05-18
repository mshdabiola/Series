package com.mshdabiola.ui.state

enum class ExamType(val secondPerQuestion: Int, val save: Boolean = false) {
    YEAR(40, true),
    RANDOM(40, true),
    FAST_FINGER(20),
}
