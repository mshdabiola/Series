package com.mshdabiola.questionscreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
internal fun QuestionScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val viewModel: QuestionViewModel = koinViewModel()

    QuestionScreen(
        profileState = ProfileState(),
        back = onBack,
        onFinish = onFinish
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun QuestionScreen(
    profileState: ProfileState,
    back: () -> Unit = {},
    onFinish: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true },
        topBar = {
            TopAppBar(
                title = { Text(text = "Question") },
                navigationIcon = {
                    IconButton(onClick = back) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "back"
                        )
                    }

                }

            )
        },

        ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(horizontal = 8.dp)
        ) {

            Button(onClick = onFinish) {
                Text(text = "To finish")
            }
        }
    }

}

@Preview
@Composable
fun QuestionScreenPreview() {
    QuestionScreen(
        profileState = ProfileState()
    )
}
