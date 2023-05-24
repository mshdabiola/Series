package com.mshdabiola.series.feature.exam.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
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
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.series.feature.exam.state.ItemUi
import com.mshdabiola.ui.draganddrop.DragAndDropImage
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(
    modifier: Modifier = Modifier,
    items: ImmutableList<ItemUi>,
    editMode: Boolean = false,
    addUp: (Int) -> Unit = {},
    addBottom: (Int) -> Unit = {},
    delete: (Int) -> Unit = {},
    moveUp: (Int) -> Unit = {},
    moveDown: (Int) -> Unit = {},
    edit: (Int) -> Unit = {},
    changeType: (Int,Int) -> Unit = {_,_->},
    onTextChange: (Int, String) -> Unit = { _, _ -> },

) {


    Column(modifier) {
        items.forEachIndexed { index, item ->
            var showContext by remember { mutableStateOf(false) }
            var showChange by remember { mutableStateOf(false) }

            ListItem(
                modifier = Modifier.fillMaxWidth(),
                headlineText = {
                    val childModifier = Modifier.fillMaxWidth()

                    when (item) {
                        is ItemUi.Equation -> EquationContent(childModifier, item, onTextChange = {
                            onTextChange(index, it)
                        })

                        is ItemUi.Text -> TextContent(childModifier, item, onTextChange = {
                            onTextChange(index, it)
                        })

                        is ItemUi.Image -> ImageContent(childModifier, item, onTextChange = {
                            onTextChange(index, it)
                        })
                    }
                },
                trailingContent = {
                    if (editMode) {
                        Box {
                            IconButton(onClick = { showContext = true }) {
                                Icon(Icons.Default.MoreVert, "")
                            }

                            DropdownMenu(
                                expanded = showContext,
                                onDismissRequest = { showContext = false }) {
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                    text = { Text("Add Top") },
                                    onClick = {
                                        addUp(index)
                                        showContext = false
                                    })
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Add, "delete") },
                                    text = { Text("Add Down") },
                                    onClick = {
                                        addBottom(index)
                                        showContext = false
                                    })
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.Delete, "delete") },
                                    text = { Text("Delete") },
                                    onClick = {
                                        delete(index)
                                        showContext = false
                                    })
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.MoveUp, "delete") },
                                    text = { Text("Move up") },
                                    onClick = {
                                        moveUp(index)
                                        showContext = false
                                    })
                                DropdownMenuItem(
                                    leadingIcon = { Icon(Icons.Default.MoveDown, "delete") },
                                    text = { Text("Move down") },
                                    onClick = {
                                        moveDown(index)
                                        showContext = false
                                    })
                                DropdownMenuItem(
                                    leadingIcon = {
                                        if (!item.isEditMode) Icon(
                                            Icons.Default.Edit,
                                            "edit"
                                        ) else Icon(Icons.Default.ViewAgenda, "view")
                                    },
                                    text = { Text(if (!item.isEditMode) "Edit" else "View") },
                                    onClick = {
                                        edit(index)
                                        showContext = false
                                    })
                                Box {
                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Icon(Icons.Default.ChangeCircle, "change")
                                        },
                                        text = { Text("Change Type") },
                                        onClick = {
                                            showChange = true
                                        })
                                    DropdownMenu(
                                        expanded = showChange,
                                        onDismissRequest = { showChange = false }) {
                                        if (item !is ItemUi.Image) {
                                            DropdownMenuItem(text = { Text("Image") }, onClick = {
                                                changeType(index,2)
                                                showChange = false
                                                showContext = false
                                            })
                                        }

                                        if (item !is ItemUi.Equation) {
                                            DropdownMenuItem(text = { Text("Equation") }, onClick = {
                                                changeType(index,1)
                                                showChange = false
                                                showContext = false
                                            })
                                        }

                                        if (item !is ItemUi.Text) {
                                            DropdownMenuItem(text = { Text("Text") }, onClick = {

                                               changeType(index,0)
                                                showChange = false
                                                showContext = false
                                            })
                                        }

                                    }
                                }
                            }
                        }
                    }
                }
            )
        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EquationContent(
    modifier: Modifier = Modifier,
    equation: ItemUi.Equation,
    onTextChange: (String) -> Unit = {}
) {
    if (equation.isEditMode)
        TextField(modifier = modifier, value = equation.tex, onValueChange = onTextChange)
    else
        Text(modifier = modifier, text = equation.tex)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageContent(
    modifier: Modifier = Modifier,
    image: ItemUi.Image,
    onTextChange: (String) -> Unit = {}
) {
    Box(modifier, contentAlignment = Alignment.Center) {

        DragAndDropImage(
            modifier = Modifier.size(100.dp),
            path = image.imageName,
            onPathChange = onTextChange
        )
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TextContent(
    modifier: Modifier = Modifier,
    text: ItemUi.Text,
    onTextChange: (String) -> Unit = {}
) {
    if (text.isEditMode)
        TextField(modifier = modifier, value = text.tex, onValueChange = onTextChange)
    else
        Text(modifier = modifier, text = text.tex)

}

@Preview
@Composable
fun ContentPreview() {
    Content(items = listOf(ItemUi.Image("3ablate", true), ItemUi.Text("moshood")).toImmutableList())
}


