package com.mshdabiola.detail.topic

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.mshdabiola.ui.state.TopicUiState

@Composable
fun TopicUi(
    modifier: Modifier=Modifier,
    topicUiState: TopicUiState,
    isSelect :Boolean=false,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
) {
    var showDrop by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier,
        headlineContent = {
            Text(topicUiState.name)
        },
        trailingContent = {
            Box {
                IconButton(onClick = { showDrop = true }) {
                    Icon(Icons.Default.MoreVert, "more")
                }
                DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {
                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Update, "update") },
                        text = { Text("Update") },
                        onClick = {
                            onUpdate(topicUiState.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(topicUiState.id)
                            showDrop = false
                        },
                    )
                }
            }
        },
        colors = ListItemDefaults.colors(
            if (isSelect) MaterialTheme.colorScheme.primaryContainer else ListItemDefaults.containerColor)
    )
}
