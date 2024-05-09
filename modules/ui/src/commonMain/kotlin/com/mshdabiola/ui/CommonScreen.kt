package com.mshdabiola.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen(
    screenSize: ScreenSize,
    firstScreen: @Composable (Modifier) -> Unit,
    secondScreen: @Composable () -> Unit,
    action: @Composable RowScope.() -> Unit = {},
    topbar: @Composable () -> Unit = {},
) {
    if (screenSize <= ScreenSize.MEDIUM) {
        var show by remember { mutableStateOf(false) }

        Scaffold(
            bottomBar = {
                BottomAppBar(floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = {
                        show = true
                    }) {
                        Icon(Icons.Default.Add, "add")
                        Spacer(Modifier.width(8.dp))
                        Text("Add")
                    }
                }, actions = action)
            },

            ) {
            firstScreen(Modifier.padding(it))
            if (show) {
                ModalBottomSheet(
                    onDismissRequest = { show = false },
                    Modifier.fillMaxSize(),
                    sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
                ) {
                    secondScreen()
                }
            }
        }
    } else {
        Scaffold(
            topBar = topbar,
        ) {
            Row(modifier = Modifier.padding(it).fillMaxSize()) {
                Column(Modifier.weight(0.55f)) {
                    firstScreen(Modifier)
                }
                Column(Modifier.weight(0.45f)) {
                    secondScreen()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonScreen2(
    screenSize: ScreenSize,
    firstScreen: @Composable (Modifier) -> Unit,
    secondScreen: @Composable () -> Unit,
    show: Boolean = false,
    onDismiss: () -> Unit = {},
) {
    if (screenSize <= ScreenSize.MEDIUM) {
        firstScreen(Modifier)
        if (show) {
            ModalBottomSheet(
                onDismissRequest = onDismiss,
                Modifier.fillMaxSize(),
                sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
            ) {
                secondScreen()
            }
        }
    } else {
        Row(modifier = Modifier.fillMaxSize()) {
            Column(Modifier.weight(0.55f)) {
                firstScreen(Modifier)
            }
            Column(Modifier.weight(0.45f)) {
                secondScreen()
            }
        }
    }
}
