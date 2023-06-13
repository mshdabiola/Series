package com.mshdabiola.ui.questionui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.onClick
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.QuestionUiState


@Composable
fun QuestionEditUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    generalPath: String,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    edit: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> }
) {

    Column(modifier) {
        Content(
            items = questionUiState.content,
            generalPath = generalPath,
            addUp = { addUp(-1, it) },
            addBottom = { addBottom(-1, it) },
            delete = { delete(-1, it) },
            moveUp = { moveUp(-1, it) },
            moveDown = { moveDown(-1, it) },
            edit = { edit(-1, it) },
            changeType = { i, t -> changeType(-1, i, t) },
            onTextChange = { i, s -> onTextChange(-1, i, s) }

        )

        questionUiState.options.chunked(2).forEachIndexed { index1, optionsUiStates ->
            Row {
                optionsUiStates.forEachIndexed { index2, optionsUiState ->
                    val i = index2 + (index1 * 2)
                    Content(
                        modifier = Modifier.weight(0.5f),
                        items = optionsUiState.content,
                        generalPath = generalPath,
                        addUp = { addUp(i, it) },
                        addBottom = { addBottom(i, it) },
                        delete = { delete(i, it) },
                        moveUp = { moveUp(i, it) },
                        moveDown = { moveDown(i, it) },
                        edit = { edit(i, it) },
                        changeType = { ii, t -> changeType(i, ii, t) },
                        onTextChange = { idn, s -> onTextChange(i, idn, s) }

                    )
                }
            }
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun QuestionUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    generalPath: String,
    onUpdate: (Long) -> Unit = {},
    onMoveUp: (Long) -> Unit = {},
    onMoveDown: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> }
) {
    var showDrop by remember {
        mutableStateOf(false)
    }
    OutlinedCard(modifier) {
        Column {
            ListItem(
                headlineText = {
                    ContentView(
                        items = questionUiState.content,
                        generalPath = generalPath
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
                                    onUpdate(questionUiState.id)
                                    showDrop = false
                                })

                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.ArrowUpward, "up") },
                                text = { Text("Move Up") },
                                onClick = {
                                    onMoveUp(questionUiState.id)
                                    showDrop = false
                                })
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.ArrowDownward, "down") },
                                text = { Text("Move Down") },
                                onClick = {
                                    onMoveDown(questionUiState.id)
                                    showDrop = false
                                })

                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                                text = { Text("Delete") },
                                onClick = {
                                    onDelete(questionUiState.id)
                                    showDrop = false
                                })


                        }
                    }


                }
            )

            questionUiState.options.chunked(2).forEach { optionsUiStates ->
                Row {

                    optionsUiStates.forEach { optionsUiState ->
                        ContentView(
                            modifier = Modifier
                                .weight(0.5f)
                                .onClick {
                                    onAnswer(questionUiState.id, optionsUiState.id)
                                },
                            color = if (optionsUiState.isAnswer)
                                Color.Green.copy(alpha = 0.5f)
                            else ListItemDefaults.containerColor,

                            items = optionsUiState.content,
                            generalPath = generalPath
                        )

                    }

                }
            }

        }
    }
}

