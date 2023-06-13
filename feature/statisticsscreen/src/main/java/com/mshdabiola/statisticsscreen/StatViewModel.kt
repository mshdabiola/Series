package com.mshdabiola.statisticsscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository


class StatViewModel constructor(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {


}
