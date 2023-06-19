package com.mshdabiola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun QuestionUi(
    number: Long,
    title: String,
    content: ImmutableList<ItemUiState>,
    isInstruction: Boolean,
    generalPath: String,
    onInstruction: () -> Unit = {}
) {
    val childModifier = Modifier.fillMaxWidth()
    Card(
    ) {

        Column(
            modifier = Modifier.padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Question $number")
                Text(title)
            }

            ItemUi(content, generalPath)

            if (isInstruction) {
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = onInstruction
                ) {
                    Text("Read Instruction")
                }
            }
        }

    }

}

@Composable
fun ItemUi(items: ImmutableList<ItemUiState>, generalPath: String) {

    items.forEach { item ->
        when (item.type) {
            Type.EQUATION -> {
                EquationUi(equation = item)
            }

            Type.TEXT -> {
                Text(item.content)
            }

            Type.IMAGE -> {
                ImageUi(
                    path = "$generalPath/${item.content}",
                    contentDescription = item.content
                )
            }
        }
    }


}


@Composable
internal expect fun QuestionUiPreview()


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionsUi(
    optionUiStates: ImmutableList<OptionUiState>,
    generalPath: String,
    onClick: (Long) -> Unit = {}

) {

    val noRow = remember {
        val isLong = optionUiStates
            .map { it.content }
            .any { itemUiStates ->
                itemUiStates.filter { it.type == Type.TEXT }
                    .any { it.content.length > 15 }
            }
        if (isLong) 1 else 2

    }
    FlowRow (
        maxItemsInEachRow = noRow
    ){
        optionUiStates.forEach {
            OptionUi(
                modifier = Modifier.padding(2.dp).weight(1f),
                optionUiState = it,
                generalPath = generalPath,
                onClick = { onClick(it.id) })
        }
    }


}

@Composable
internal expect fun OptionsUiPreview()


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionUi(
    modifier: Modifier = Modifier,
    optionUiState: OptionUiState,
    generalPath: String,
    onClick: () -> Unit = {}

) {
    val color = when {
        optionUiState.isAnswer -> Color.Green
        optionUiState.choose -> MaterialTheme.colorScheme.primary
        else -> MaterialTheme.colorScheme.primaryContainer
    }
    Card(
        modifier = modifier.heightIn(min=48.dp),
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    )
    {
        Column(Modifier.padding(4.dp)) {
            ItemUi(optionUiState.content, generalPath)
        }
    }


    //}
}


@Composable
internal expect fun EquationUi(
    modifier: Modifier = Modifier,
    equation: ItemUiState
)


@Composable
expect fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    onPathChange: (String) -> Unit = {}
)

@Composable
expect fun ImageUi(
    modifier: Modifier = Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
)
