package com.mshdabiola.series.screen.question

import com.mshdabiola.model.Model


//sealed interface MainState {
//    data class Show(val models: List<ModelUiState>) : MainState
//    object Error : MainState
//
//    object Loading : MainState
//}

data class ProfileState(
    val name: String = "abiola"
)

data class ModelUiState(val id: Long?, val name: String)

fun Model.asModelUiState() = ModelUiState(id, name)
