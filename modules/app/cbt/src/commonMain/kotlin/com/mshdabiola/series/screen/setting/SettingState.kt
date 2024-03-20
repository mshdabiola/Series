package com.mshdabiola.series.screen.setting

import com.mshdabiola.model.UserData

// sealed interface MainState {
//    data class Show(val models: List<ModelUiState>) : MainState
//    object Error : MainState
//
//    object Loading : MainState
// }

data class SettingState(
    val userData: UserData,
)
