package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.OptionUiState
import kotlinx.collections.immutable.ImmutableList


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
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color),
        shape = RoundedCornerShape(4.dp),
        onClick = onClick
    )
    {
        Column(Modifier.heightIn(min=48.dp).fillMaxWidth().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            ItemUi(optionUiState.content, generalPath)
        }
    }


    //}
}

