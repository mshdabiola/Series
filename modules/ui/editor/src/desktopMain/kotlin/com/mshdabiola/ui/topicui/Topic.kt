package com.mshdabiola.ui.topicui

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.mshdabiola.ui.state.TopicUiState

@Composable
fun TopicUi(
    topicUiState: TopicUiState,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {}
) {
    var showDrop by remember { mutableStateOf(false) }
    ListItem(
        headlineContent = {
            Text("${topicUiState.id} - ${topicUiState.name}")
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
                        })

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(topicUiState.id)
                            showDrop = false
                        })


                }
            }
        }
    )

}