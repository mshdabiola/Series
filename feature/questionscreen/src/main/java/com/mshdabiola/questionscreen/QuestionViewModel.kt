package com.mshdabiola.questionscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository


class QuestionViewModel constructor(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {


}
