package com.mshdabiola.ui.examui

import androidx.compose.foundation.layout.Box
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.mshdabiola.ui.ExamUiDesktop

@Composable
fun ExamUi(
    modifier: Modifier = Modifier,
    examUiState: ExamUiDesktop,
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    toggleSelect: (Long) -> Unit = {},
    isSelectMode: Boolean = false
) {
    var showDrop by remember { mutableStateOf(false) }
    ListItem(
        modifier = modifier,
        colors = if (examUiState.isSelected)
            ListItemDefaults.colors(containerColor =
            MaterialTheme.colorScheme.primaryContainer)
        else
            ListItemDefaults.colors(),
        headlineContent = {
            Text(examUiState.subject)
        },
        supportingContent = { Text(examUiState.year.toString()) },
        trailingContent = {
            if (!isSelectMode) {
                Box {
                    IconButton(onClick = { showDrop = true }) {
                        Icon(Icons.Default.MoreVert, "more")
                    }
                    DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {


                        DropdownMenuItem(
                            leadingIcon = { Icon(Icons.Default.Update, "update") },
                            text = { Text("Update") },
                            onClick = {
                                onUpdate(examUiState.id)
                                showDrop = false
                            })

                        DropdownMenuItem(
                            leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                            text = { Text("Delete") },
                            onClick = {
                                onDelete(examUiState.id)
                                showDrop = false
                            })

                        DropdownMenuItem(
                            leadingIcon = { Icon(Icons.Default.HdrOnSelect, "Select") },
                            text = { Text("Select") },
                            onClick = {
                                toggleSelect(examUiState.id)
                                showDrop = false
                            })


                    }
                }
            }
        }
    )

}