package com.mshdabiola.profilescreen

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
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ProfileScreen() {
    val viewModel: ProfileViewModel = koinViewModel()

    ProfileScreen(
        profileState = ProfileState()
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun ProfileScreen(
    profileState: ProfileState
) {
    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true },
        topBar = {
            TopAppBar(
                title = { Text(text = "Profile") },

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
fun ProfileScreenPreview() {
    ProfileScreen(
        profileState = ProfileState()
    )
}
