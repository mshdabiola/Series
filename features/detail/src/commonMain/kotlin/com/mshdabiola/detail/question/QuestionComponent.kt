package com.mshdabiola.detail.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
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
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.image.Content
import com.mshdabiola.ui.image.ContentView
import com.mshdabiola.ui.state.QuestionUiState

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun QuestionEditUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    edit: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> },
    fillIt: Boolean = false,
) {
    Column(modifier) {
        Content(
            items = questionUiState.contents,
            examId = questionUiState.examId,
            addUp = { addUp(-1, it) },
            addBottom = { addBottom(-1, it) },
            delete = { delete(-1, it) },
            moveUp = { moveUp(-1, it) },
            moveDown = { moveDown(-1, it) },
            edit = { edit(-1, it) },
            changeType = { i, t -> changeType(-1, i, t) },
            onTextChange = { i, s -> onTextChange(-1, i, s) },

            )

//        questionUiState.options.chunked(2).forEachIndexed { index1, optionsUiStates ->
//            Row {
//                optionsUiStates.forEachIndexed { index2, optionsUiState ->
//                    val i = index2 + (index1 * 2)
//                    Content(
//                        modifier = Modifier.weight(0.5f),
//                        items = optionsUiState.content,
//                        examId = questionUiState.examId,
//                        addUp = { addUp(i, it) },
//                        addBottom = { addBottom(i, it) },
//                        delete = { delete(i, it) },
//                        moveUp = { moveUp(i, it) },
//                        moveDown = { moveDown(i, it) },
//                        edit = { edit(i, it) },
//                        changeType = { ii, t -> changeType(i, ii, t) },
//                        onTextChange = { idn, s -> onTextChange(i, idn, s) }
//
//                    )
//                }
//            }
//        }

        FlowRow(modifier = Modifier.fillMaxWidth(), maxItemsInEachRow = 2) {
            questionUiState.options?.forEachIndexed { i, optionUiState ->

//                Box(modifier=Modifier.height(20.dp).fillMaxWidth(0.49f).background(if(i%2==0)Color.Blue else Color.Black))

                Content(
                    modifier = Modifier.fillMaxWidth(if (fillIt) 1f else 0.499999f), // .weight(0.5f)
                    items = optionUiState.content,
                    examId = questionUiState.examId,
                    addUp = { addUp(i, it) },
                    addBottom = { addBottom(i, it) },
                    delete = { delete(i, it) },
                    moveUp = { moveUp(i, it) },
                    moveDown = { moveDown(i, it) },
                    edit = { edit(i, it) },
                    changeType = { ii, t -> changeType(i, ii, t) },
                    onTextChange = { idn, s -> onTextChange(i, idn, s) },

                    )
            }
        }

        if (questionUiState.answers != null) {
            Spacer(Modifier.height(4.dp))
            Text("Answer", modifier = Modifier.padding(horizontal = 16.dp))
            Content(
                items = questionUiState.answers!!,
                examId = questionUiState.id,
                addUp = { addUp(-2, it) },
                addBottom = { addBottom(-2, it) },
                delete = { delete(-2, it) },
                moveUp = { moveUp(-2, it) },
                moveDown = { moveDown(-2, it) },
                edit = { edit(-2, it) },
                changeType = { i, t -> changeType(-2, i, t) },
                onTextChange = { i, s -> onTextChange(-2, i, s) },

                )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun QuestionUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    onUpdate: (Long) -> Unit = {},
    onMoveUp: (Long) -> Unit = {},
    onMoveDown: (Long) -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
) {
    var showDrop by remember {
        mutableStateOf(false)
    }
    OutlinedCard(modifier) {
        Column {
            Row(
                modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    if (questionUiState.isTheory) "Theory" else "Objective",
                )
                Text("Number - ${questionUiState.number}")
                questionUiState.instructionUiState?.let {
                    Text("Instruction id - ${it.id}")
                }

                questionUiState.topicUiState?.let {
                    Text("Topic id - ${it.id}")
                }
            }

            ListItem(
                headlineContent = {
                    ContentView(
                        items = questionUiState.contents,
                        examId = questionUiState.examId,
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
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.ArrowUpward, "up") },
                                text = { Text("Move Up") },
                                onClick = {
                                    onMoveUp(questionUiState.id)
                                    showDrop = false
                                },
                            )
                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.ArrowDownward, "down") },
                                text = { Text("Move Down") },
                                onClick = {
                                    onMoveDown(questionUiState.id)
                                    showDrop = false
                                },
                            )

                            DropdownMenuItem(
                                leadingIcon = { Icon(Icons.Default.Delete, "Delete") },
                                text = { Text("Delete") },
                                onClick = {
                                    onDelete(questionUiState.id)
                                    showDrop = false
                                },
                            )
                        }
                    }
                },
            )

            questionUiState.options?.chunked(2)?.forEach { optionsUiStates ->
                Row {
                    optionsUiStates.forEach { optionsUiState ->
                        ContentView(
                            modifier = Modifier
                                .weight(0.5f)
                                .clickable(onClick = {
                                    onAnswer(
                                        questionUiState.id,
                                        optionsUiState.id,
                                    )
                                }),
                            color = if (optionsUiState.isAnswer) {
                                Color.Green.copy(alpha = 0.5f)
                            } else {
                                ListItemDefaults.containerColor
                            },

                            items = optionsUiState.content,
                            examId = questionUiState.examId,
                        )
                    }
                }
            }

            if (questionUiState.answers != null) {
                Spacer(Modifier.height(4.dp))
                Text("Answer", modifier = Modifier.padding(horizontal = 16.dp))
                ContentView(
                    items = questionUiState.answers!!,
                    examId = questionUiState.examId,

                    )
            }
        }
    }
}
