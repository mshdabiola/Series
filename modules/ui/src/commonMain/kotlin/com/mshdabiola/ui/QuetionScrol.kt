package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.collections.immutable.ImmutableList

@Composable
fun QuestionScroll(
    currentQuestion: Int,
    showPrev: Boolean,
    showNext: Boolean,
    chooses: ImmutableList<Int>,
    onChooseClick: (Int) -> Unit = {},
    onNext: () -> Unit = {},
    onPrev: () -> Unit = {},
) {
    val state = rememberLazyListState()

    LaunchedEffect(currentQuestion) {
        println("current index $currentQuestion")
        val value = if (currentQuestion == 0) 0 else currentQuestion - 1
        state.scrollToItem(value)
    }

    val number = remember(chooses) { chooses.size }
    val noAnswer = remember(chooses) {
        derivedStateOf {
            chooses.count { it > -1 }
        }
    }
    Column(
        verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        ) {
            IconButton(
                enabled = showPrev,
                onClick = onPrev,
            ) {
                Icon(Icons.Default.KeyboardArrowLeft, "prev")
            }

            LazyRow(
                state = state,
                modifier = Modifier.weight(1f),
                horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
            ) {
                items(count = number, key = { it }) {
                    QuestionNumberButton(
                        number = it,
                        isChoose = chooses[it] > -1,
                        isCurrent = it == currentQuestion,
                    ) { onChooseClick(it) }
                }
            }
            IconButton(
                enabled = showNext,
                onClick = onNext,
            ) {
                Icon(Icons.Default.KeyboardArrowRight, "next")
            }
        }

        Text("${noAnswer.value} of $number")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionNumberButton(
    number: Int,
    isChoose: Boolean,
    isCurrent: Boolean = false,
    onClick: () -> Unit = {},
) {
    val color = if (isChoose) {
        MaterialTheme.colorScheme.primaryContainer
    } else {
        MaterialTheme.colorScheme.surface
    }
    val border = isCurrent || !isChoose

    OutlinedCard(
        modifier = Modifier.requiredSize(48.dp),
        shape = CircleShape,
        colors = CardDefaults.outlinedCardColors(containerColor = color),
        border = CardDefaults.outlinedCardBorder(border),
        onClick = onClick,
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("${number + 1}")
        }
    }
}
