package com.mshdabiola.profilescreen

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mshdabiola.data.repository.inter.IModelRepository


class ProfileViewModel constructor(
    private val savedStateHandle: SavedStateHandle,
    private val modelRepository: IModelRepository,
) : ViewModel() {


}
