package com.mshdabiola.ui.image

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChangeCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.MoveDown
import androidx.compose.material.icons.filled.MoveUp
import androidx.compose.material.icons.filled.ViewAgenda
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.ImageUtil
import com.mshdabiola.model.data.Type
import com.mshdabiola.retex.Latex
import com.mshdabiola.ui.MarkUpText
import com.mshdabiola.ui.state.ItemUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ContentView(
    modifier: Modifier = Modifier,
    color: Color = ListItemDefaults.containerColor,
    items: ImmutableList<ItemUiState>,
    examId: Long,
) {
    Column(modifier) {
        items.forEachIndexed { _, item ->

            ListItem(
                modifier = Modifier.fillMaxWidth(),
                colors = ListItemDefaults.colors(containerColor = color),
                headlineContent = {
                    val childModifier = Modifier.fillMaxWidth()

                    when (item.type) {
                        Type.EQUATION -> Box(childModifier, contentAlignment = Alignment.Center) {
                            Latex(modifier = Modifier, item.content)
                        }

                        Type.TEXT -> MarkUpText(modifier = childModifier, text = item.content)

                        Type.IMAGE ->
                            Box(childModifier, contentAlignment = Alignment.Center) {
                                ImageUi(
                                    Modifier.size(100.dp),
                                    path = ImageUtil.getGeneralDir(item.content, examId).path,
                                    contentDescription = "",
                                )
                            }
                    }
                },

                )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    items: ImmutableList<ItemUiState>,
    examId: Long,
    addUp: (Int) -> Unit = {},
    addBottom: (Int) -> Unit = {},
    delete: (Int) -> Unit = {},
    moveUp: (Int) -> Unit = {},
    moveDown: (Int) -> Unit = {},
    edit: (Int) -> Unit = {},
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onTextChange: (Int, String) -> Unit = { _, _ -> },

    ) {
    Column(modifier) {
        items.forEachIndexed { index, item ->
            var showContext by remember { mutableStateOf(false) }
            var showChange by remember { mutableStateOf(false) }

            ListItem(
                modifier = Modifier.fillMaxWidth(),
                headlineContent = {
                    val childModifier = Modifier.fillMaxWidth()

                    when (item.type) {
                        Type.EQUATION -> EquationContent(childModifier, item, onTextChange = {
                            onTextChange(index, it)
                        })

                        Type.TEXT -> TextContent(childModifier, item, onTextChange = {
                            onTextChange(index, it)
                        })

                        Type.IMAGE -> ImageContent(
                            childModifier,
                            item,
                            examId = examId,
                            onTextChange = {
                                onTextChange(index, it)
                            },
                        )
                    }
                },
                trailingContent = {
                    Box {
                        IconButton(onClick = { showContext = true }) {
                            Icon(Icons.Default.MoreVert, "")
                        }

                        DropdownMenu(
                            expanded = showContext,
                            onDismissRequest = {
                                showContext = false
                                showChange = false
                            },
                        ) {
                            if (showChange) {
                                if (item.type != Type.IMAGE) {
                                    DropdownMenuItem(text = { Text("Image") }, onClick = {
                                        changeType(index, Type.IMAGE)
                                        showChange = false
                                        showContext = false
                                    })
                                }

                                if (item.type != Type.EQUATION) {
                                    DropdownMenuItem(
                                        text = { Text("Equation") },
                                        onClick = {
                                            changeType(index, Type.EQUATION)
                                            showChange = false
                                            showContext = false
                                        },
                                    )
                                }

                                if (item.type != Type.TEXT) {
                                    DropdownMenuItem(text = { Text("Text") }, onClick = {
                                        changeType(index, Type.TEXT)
                                        showChange = false
                                        showContext = false
                                    })
                                }
                            } else {
                                DropdownMenuItem(
                                    leadingIcon = {
                                        if (!item.isEditMode) {
                                            Icon(
                                                Icons.Default.Edit,
                                                "edit",
                                            )
                                        } else {
                                            Icon(Icons.Default.ViewAgenda, "view")
                                        }
                                    },
                                    text = { Text(if (!item.isEditMode) "Edit" else "View") },
                                    onClick = {
                                        edit(index)
                                        showContext = false
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(Icons.Default.ChangeCircle, "change")
                                    },
                                    text = { Text("Change Type") },
                                    onClick = {
                                        showChange = true
                                    },
                                )

                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                    text = { Text("Add Top") },
                                    onClick = {
                                        addUp(index)
                                        showContext = false
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                    text = { Text("Add Down") },
                                    onClick = {
                                        addBottom(index)
                                        showContext = false
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Delete, "delete") },
                                    text = { Text("Delete") },
                                    onClick = {
                                        delete(index)
                                        showContext = false
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.MoveUp, "delete") },
                                    text = { Text("Move up") },
                                    onClick = {
                                        moveUp(index)
                                        showContext = false
                                    },
                                )
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.MoveDown, "delete") },
                                    text = { Text("Move down") },
                                    onClick = {
                                        moveDown(index)
                                        showContext = false
                                    },
                                )
                            }
                        }
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquationContent(
    modifier: Modifier = Modifier,
    equation: ItemUiState,
    onTextChange: (String) -> Unit = {},
) {
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(equation.focus) {
        if (equation.focus) {
            focusRequester.requestFocus()
        }
    }

    Box(modifier, contentAlignment = Alignment.Center) {
        if (equation.isEditMode) {
            TextField(
                modifier = Modifier.focusRequester(focusRequester).fillMaxWidth(),
                label = { Text("Equation") },
                maxLines = 1,
                value = equation.content,
                onValueChange = onTextChange,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            )
        } else {
            Latex(modifier = Modifier, equation.content)
        }
    }
}

@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    image: ItemUiState,
    examId: Long,
    onTextChange: (String) -> Unit = {},
) {
    Box(modifier, contentAlignment = Alignment.Center) {
        DragAndDropImage(
            modifier = Modifier.size(100.dp),
            path = ImageUtil.getGeneralDir(image.content, examId).path,
            onPathChange = onTextChange,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextContent(
    modifier: Modifier = Modifier,
    text: ItemUiState,
    onTextChange: (String) -> Unit = {},
) {
    val focusRequester = remember {
        FocusRequester()
    }
    LaunchedEffect(text.focus) {
        if (text.focus) {
            focusRequester.requestFocus()
        }
    }
    if (text.isEditMode) {
        TextField(
            modifier = modifier.focusRequester(focusRequester).fillMaxWidth(),
            value = text.content,
            label = { Text("Text Content") },
            maxLines = 1,
            onValueChange = onTextChange,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        )
    } else {
        MarkUpText(modifier = modifier, text = text.content)
    }
}
