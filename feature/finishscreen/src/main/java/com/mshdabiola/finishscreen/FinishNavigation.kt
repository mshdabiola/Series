package com.mshdabiola.finishscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun FinishScreenNav(onBack: () -> Unit) {
    val name = rememberSaveable {
        "rem${Random.nextLong()}"
    }
    val viewModel: FinishViewModel = koinViewModel(key = name)
    FinishScreen (
        onBack,
        viewModel
    )
}

