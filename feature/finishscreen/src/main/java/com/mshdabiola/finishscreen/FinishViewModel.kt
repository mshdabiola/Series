package com.mshdabiola.finishscreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository
import timber.log.Timber


class FinishViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {


    init {
        Timber.e("init finish")
    }
}
