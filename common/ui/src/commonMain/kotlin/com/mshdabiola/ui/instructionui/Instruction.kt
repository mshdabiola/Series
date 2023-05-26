package com.mshdabiola.ui.instructionui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.questionui.Content
import com.mshdabiola.ui.state.ItemUi
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun InstructionEditUi(
    modifier: Modifier = Modifier,
    title: String = "",
    content: ImmutableList<ItemUi> = emptyList<ItemUi>().toImmutableList(),
    onTitleChange: (String) -> Unit = {},
    addUp: (Int) -> Unit = { _ -> },
    addBottom: (Int) -> Unit = { _ -> },
    delete: (Int) -> Unit = { _ -> },
    moveUp: (Int) -> Unit = { _ -> },
    moveDown: (Int) -> Unit = { _ -> },
    edit: (Int) -> Unit = { _ -> },
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onTextChange: (Int, String) -> Unit = { _, _ -> }
) {
    Column(modifier) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = title,
            onValueChange = onTitleChange,
            maxLines = 1,
            label = { Text("Title") }
        )
        Spacer(Modifier.height(4.dp))
        Content(
            items = content,
            addUp = addUp,
            addBottom = addBottom,
            delete = delete,
            moveUp = moveUp,
            moveDown = moveDown,
            edit = edit,
            changeType = changeType,
            onTextChange = onTextChange
        )

    }

}