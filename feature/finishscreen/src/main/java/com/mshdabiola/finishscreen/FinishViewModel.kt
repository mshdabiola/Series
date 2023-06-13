package com.mshdabiola.finishscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository


class FinishViewModel constructor(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {


}
