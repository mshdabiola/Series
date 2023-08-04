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
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.series.screen.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun StatScreen() {
    val viewModel: MainViewModel = koinViewModel()

    StatScreen(
        statState = StatState()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun StatScreen(
    statState: StatState
) {
    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true },
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

@Preview
@Composable
fun StatScreenPreview() {
    StatScreen(
        statState = StatState()
    )
}
