package com.mshdabiola.series

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn( ExperimentalSplitPaneApi::class)
@Composable
actual fun CommonScreen(
  firstScreen:   @Composable  () -> Unit,
     secondScreen:   @Composable  () -> Unit,
) {
    Scaffold {  }

    val state = rememberSplitPaneState(0.5f, true)
    HorizontalSplitPane(splitPaneState = state) {

        first {
            firstScreen()
        }
        second {
           secondScreen()
        }
    }

}