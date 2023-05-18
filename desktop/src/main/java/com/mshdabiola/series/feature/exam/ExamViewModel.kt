package com.mshdabiola.series.ui.feature.exam

import com.mshdabiola.series.ViewModel

class ExamViewModel(private val id: Long) : ViewModel() {
    init {
        println("id is $id")
    }
}