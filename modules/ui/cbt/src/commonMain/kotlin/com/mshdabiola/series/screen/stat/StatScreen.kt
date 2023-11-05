package com.mshdabiola.series.screen.stat

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.semanticsCommon
import com.mshdabiola.series.screen.MainViewModel

@Composable
internal fun StatScreen() {
    val viewModel: MainViewModel = KoinCommonViewModel()

    StatScreen(
        statState = StatState()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun StatScreen(
    statState: StatState
) {
    Scaffold(
        modifier = Modifier.semanticsCommon{},
        topBar = {
            TopAppBar(
                title = { Text(text = "Stat") },

                )
        },

        ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {
        }
    }

}

@Composable
expect fun StatScreenPreview()
