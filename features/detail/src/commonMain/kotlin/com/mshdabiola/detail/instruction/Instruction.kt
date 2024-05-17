package com.mshdabiola.detail.instruction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.image.ContentView
import com.mshdabiola.ui.state.InstructionUiState

@Composable
fun InstructionEditUi(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    onTitleChange: (String) -> Unit = {},
    addUp: (Int) -> Unit = { _ -> },
    addBottom: (Int) -> Unit = { _ -> },
    delete: (Int) -> Unit = { _ -> },
    moveUp: (Int) -> Unit = { _ -> },
    moveDown: (Int) -> Unit = { _ -> },
    edit: (Int) -> Unit = { _ -> },
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onTextChange: (Int, String) -> Unit = { _, _ -> },
) {
    Column(modifier) {
        TextField(
            modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
            value = instructionUiState.title ?: "",
            onValueChange = onTitleChange,
            maxLines = 1,
            label = { Text("Title") },
        )
        Spacer(Modifier.height(4.dp))
        Content(
            items = instructionUiState.content,
            addUp = addUp,
            addBottom = addBottom,
            delete = delete,
            moveUp = moveUp,
            moveDown = moveDown,
            edit = edit,
            changeType = changeType,
            onTextChange = onTextChange,
            examId = instructionUiState.examId,
        )
    }
}

@Composable
fun InstructionUi(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    isSelect :Boolean=false,
    onUpdate: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
) {
    var showDrop by remember { mutableStateOf(false) }
    val color =
    if (isSelect) MaterialTheme.colorScheme.primaryContainer else ListItemDefaults.containerColor
    ListItem(
        modifier = modifier,
        headlineContent = {
            if (instructionUiState.title != null) {
                Text(instructionUiState.title!!)
            }
        },
        supportingContent = {
            ContentView(
                color = color,
                items = instructionUiState.content,
                examId = instructionUiState.examId,
            )
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
                            onUpdate(instructionUiState.id)
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                        text = { Text("Delete") },
                        onClick = {
                            onDelete(instructionUiState.id)
                            showDrop = false
                        },
                    )
                }
            }
        },
        colors =ListItemDefaults.colors(color)
    )
}
