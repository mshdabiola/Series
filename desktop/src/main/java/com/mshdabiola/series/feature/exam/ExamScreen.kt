package com.mshdabiola.series.ui.feature.exam

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                },

                title = { Text("Exam Screen") })
        }
    ) {
        ExamContent(
            modifier = Modifier.padding(it)
        )
    }

}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.7f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {

        }
        second {
            Text("Exm")
        }
    }

}
