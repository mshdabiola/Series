package com.mshdabiola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
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
import com.mshdabiola.ui.image.DropdownMenu
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ContinueCard(
    onClick: () -> Unit = {},
    year: Long,
    timeRemain: Long,
    progress: Float,
    part: String,
    enabled: Boolean,
) {
    val color = LocalTextStyle.current.color.copy(alpha = 0.7f)
    val timeString = remember(timeRemain) {
        String.format("%02d : %02d", timeRemain.toMinute(), timeRemain.toSecond())
    }
    Card() {
        Column(
            Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),

        ) {
            Text(
                text = "You are soon closed to end, finish your quiz and find out your scores",
                style = MaterialTheme.typography.bodyMedium,
                color = color,
            )
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                // verticalAlignment = Alignment.Start,
                maxItemsInEachRow = 2,
            ) {
                Text(modifier = Modifier.weight(0.4f), text = "Year : $year")
                Text(modifier = Modifier.weight(0.6f), text = "Remaining: $timeString")
                Text(
                    modifier = Modifier.weight(0.4f),
                    text = "Progress : ${(progress * 100).toInt()}%",
                )
                Text(modifier = Modifier.weight(0.6f), text = "Type : $part")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
            }

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = progress,
                trackColor = MaterialTheme.colorScheme.background,

            )

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick,
                enabled = enabled,
            ) {
                Text(text = "Continue Exam")
            }
        }
    }
}

@Composable
fun StartCard(
    onClick: (Int, Long) -> Unit = { _, _ -> },
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

        LaunchedEffect(yearIndex) {
            typeIndex = if (exams[yearIndex].isObjectiveOnly) {
                1
            } else {
                0
            }
        }

        Card() {
            Column(
                Modifier.padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
            ) {
                Text(
                    modifier = Modifier,
                    text = "Ready to challenge yourself with new test? Let go!",
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    YearExposed(
                        modifier = Modifier.width(130.dp),
                        exams = exams,
                        label = "Exam year",
                        selectedOptionText = yearIndex,
                    ) { yearIndex = it }

                    ExamType(
                        modifier = Modifier.width(150.dp),
                        enabled = exams.getOrNull(yearIndex)?.isObjectiveOnly == false,
                        selectedOption = typeIndex,
                        onChange = { typeIndex = it },
                    )
                }
                Button(
                    modifier = Modifier.align(Alignment.End),
                    onClick = {
                        onClick( typeIndex,exams[yearIndex].year)
                    },
                    colors = if (isSubmit) ButtonDefaults.buttonColors() else ButtonDefaults.elevatedButtonColors(),
                ) {
                    Text(text = "Start exam")
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtherCard(
    title: String,
    painter: Painter,
    contentDesc: String = "",
    onClick: () -> Unit = {},
) {
    OutlinedCard(onClick = onClick) {
        Column(
            Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

        ) {
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer)
                    .padding(16.dp),
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painter,
                    contentDescription = contentDesc,
                    tint = MaterialTheme.colorScheme.onBackground,
                )
            }

            Text(text = title, color = MaterialTheme.colorScheme.onBackground)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun YearExposed(
    modifier: Modifier,
    exams: ImmutableList<ExamUiState>,
    selectedOptionText: Int,
    label: String,
    onChange: (Int) -> Unit,
) {
    DropdownMenu(
        modifier,
        currentIndex = selectedOptionText,
        data = exams.map { it.year.toString() }.toImmutableList(),
        label = label,
        onDataChange = onChange,
    )

// We want to react on tap/press on TextField to show menu
}

@Composable
internal fun ExamType(
    modifier: Modifier,
    enabled: Boolean,
    selectedOption: Int,
    onChange: (Int) -> Unit,
) {
    val types = getExamPart()

    DropdownMenu(
        modifier,
        currentIndex = selectedOption,
        data = types.toList().toImmutableList(),
        label = "Examp type",
        onDataChange = onChange,
        enabled = enabled,
    )
}
