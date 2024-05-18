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
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.OptionUiState
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OptionsUi(
    optionUiStates: ImmutableList<OptionUiState>,
    showAnswer: Boolean = false,
    onClick: (Int) -> Unit = {},
    selectedOption: Int = -1,
    examId: Long,
) {
    val noRow = remember {
        val isLong = optionUiStates
            .map { it.content }
            .any { itemUiStates ->
                itemUiStates
                    .any {
                        when (it.type) {
                            Type.TEXT -> it.content.length > 15
                            Type.IMAGE -> true
                            else -> it.content.length > 25
                        }
                    }
            }
        if (isLong) 1 else 2
    }
    FlowRow(
        maxItemsInEachRow = noRow,
    ) {
        optionUiStates.forEachIndexed { index, optionUiState ->
            OptionUi(
                modifier = Modifier.padding(2.dp).weight(1f),
                optionUiState = optionUiState,
                showAnswer = showAnswer,
                isChoose = selectedOption == index,
                onClick = { onClick(index) },
                examId = examId,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OptionUi(
    modifier: Modifier = Modifier,
    optionUiState: OptionUiState,
    showAnswer: Boolean = false,
    isChoose: Boolean = false,
    onClick: () -> Unit = {},
    examId: Long,
) {
    val color = when {
        optionUiState.isAnswer && showAnswer -> CardDefaults.cardColors(
            containerColor = correctContainer(),
            contentColor = onCorrectContainer(),
        )

        isChoose && showAnswer -> CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer)
        isChoose -> CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
        showAnswer -> CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
        else -> CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    }

    Card(
        modifier = modifier,
        colors = color,
        shape = RoundedCornerShape(4.dp),
        onClick = onClick,
    ) {
        Column(
            Modifier.heightIn(min = 48.dp).fillMaxWidth().padding(4.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            ItemUi(optionUiState.content, examId)
        }
    }
}
