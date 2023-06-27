package com.mshdabiola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList

@Composable
fun ContinueCard(
    onClick: () -> Unit = {},
    year: Long,
    progress: Float,
    isSubmit: Boolean,
) {
    val color = LocalTextStyle.current.color.copy(alpha = 0.7f)
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

            Text("Year : $year")


            Text("Question progress : ${(progress * 100).toInt()}%")


            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = progress,
                trackColor = MaterialTheme.colorScheme.background

            )

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick,
                enabled = !isSubmit
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
    onClick: (Int) -> Unit = {},
    exams: ImmutableList<ExamUiState>,
    isSubmit: Boolean,
) {
    if (exams.isNotEmpty()) {
        var select by remember {
            mutableStateOf(0)
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
                        modifier = Modifier.width(200.dp),
                        exams = exams,
                        supportText = "Exam year",
                        selectedOptionText = select
                    ) { select = it }

                    Button(
                        onClick = {
                            onClick(select)
                        },
                        colors = if (isSubmit) ButtonDefaults.buttonColors() else ButtonDefaults.elevatedButtonColors()
                    ) {
                        Text(text = "Start exam")
                    }

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
    supportText: String = "",
    onChange: (Int) -> Unit = {}
)

