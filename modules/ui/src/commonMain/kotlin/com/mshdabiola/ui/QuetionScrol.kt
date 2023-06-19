package com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.QuestionNumberUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun QuestionScroll(
    quetions: ImmutableList<QuestionNumberUiState>
) {

    val number = remember { quetions.size }
    val noAnswer = remember {
        derivedStateOf {
            quetions.count { it.isChoose }
        }
    }
    Column (
        verticalArrangement = Arrangement.spacedBy(4.dp,Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        LazyRow(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            items(items = quetions, key = {it.number}){
                QuestionNumberButton(it)
            }
        }

        Text("${noAnswer.value} of $number")

        TextButton(onClick = {}){
            Text("Show all questions")
        }
    }

}

@Composable
internal expect fun QuestionScrollPreview()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuestionNumberButton(
    questionNumberUiState: QuestionNumberUiState,
    onClick : ()->Unit={}
) {
    val color = if (questionNumberUiState.isChoose)
        MaterialTheme.colorScheme.primaryContainer
    else
        MaterialTheme.colorScheme.surface
    OutlinedCard(
        modifier = Modifier.size(48.dp),
        shape = CircleShape,
        colors = CardDefaults.outlinedCardColors(containerColor = color),
        onClick = onClick
    ) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("${questionNumberUiState.number}")
        }

    }
}

@Composable
internal expect fun QuestionNumberButtonPreview()