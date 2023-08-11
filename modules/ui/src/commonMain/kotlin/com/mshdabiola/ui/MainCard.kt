package com.mshdabiola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContinueCard(
    onClick: () -> Unit = {},
    year: Long,
    time2: Long,
    progress: Float,
    part : String,
    enabled: Boolean,
) {
    val color = LocalTextStyle.current.color.copy(alpha = 0.7f)
    val timeString = remember(time2) {
        val instant = Instant.fromEpochSeconds(time2)
        val time = instant.toLocalDateTime(TimeZone.UTC).time
        String.format("%02d : %02d", time.minute, time.second)
    }
    Card() {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)

        ) {
            Text(
                text = "You are soon closed to end, finish your quiz and find out your scores",
                style = MaterialTheme.typography.bodySmall,
                color = color
            )
            FlowRow (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
               // verticalAlignment = Alignment.Start,
                maxItemsInEachRow = 2
            ) {
                Text(modifier = Modifier.weight(0.4f), text = "Year : $year")
                Text(modifier = Modifier.weight(0.6f), text = "Remaining: $timeString")
                Text(modifier = Modifier.weight(0.4f), text = "Progress : ${(progress * 100).toInt()}%")
                Text(modifier = Modifier.weight(0.6f), text = "Type : $part")
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){

            }





            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = progress,
                trackColor = MaterialTheme.colorScheme.background

            )

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick,
                enabled = enabled
            ) {
                Text(text = "Continue Exam")
            }

        }
    }
}

@Composable
internal expect fun ContinueCardPreview()

@Composable
fun StartCard(
    onClick: (Int,Int) -> Unit = {_,_->},
    exams: ImmutableList<ExamUiState>,
    isSubmit: Boolean,
) {
    if (exams.isNotEmpty()) {
        var yearIndex by rememberSaveable {
            mutableStateOf(0)
        }
        var typeIndex by rememberSaveable {
            mutableStateOf(0)
        }

        LaunchedEffect(yearIndex){
            typeIndex = if (exams[yearIndex].isObjOnly){
                1
            }else{
                0
            }
        }

        Card() {
            Column(
                Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    modifier = Modifier,
                    text = "Ready to challenge yourself with new test? Let go!"
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    YearExposed(
                        modifier = Modifier.width(130.dp),
                        exams = exams,
                        label = "Exam year",
                        selectedOptionText = yearIndex
                    ) { yearIndex = it }

                    ExamType(
                        modifier = Modifier.width(150.dp),
                        enabled = exams.getOrNull(yearIndex)?.isObjOnly==false,
                        selectedOption = typeIndex,
                        onChange = {typeIndex=it}
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        onClick(yearIndex,typeIndex)
                    },
                    colors = if (isSubmit) ButtonDefaults.buttonColors() else ButtonDefaults.elevatedButtonColors()
                ) {
                    Text(text = "Start exam")
                }
            }

        }
    }

}

@Composable
internal expect fun StatCardPreview()

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherCard(
    title: String,
    painter: Painter,
    contentDesc: String = "",
    onClick: () -> Unit = {}
) {
    OutlinedCard(onClick = onClick) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painter,
                    contentDescription = contentDesc,
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }

            Text(text = title, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@Composable
internal expect fun OtherCardPreview()

@Composable
internal expect fun YearExposed(
    modifier: Modifier = Modifier,
    exams: ImmutableList<ExamUiState>,
    selectedOptionText: Int,
    label: String = "",
    onChange: (Int) -> Unit = {}
)

@Composable
internal expect fun ExamType(
    modifier: Modifier = Modifier,
    enabled: Boolean,
    selectedOption: Int,
    onChange: (Int) -> Unit = {}
)

