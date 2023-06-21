package com.mshdabiola.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ContinueCard(
    onClick: () -> Unit = {}
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

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Year :", color = color, style = MaterialTheme.typography.bodySmall)
                Text("2015")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Question progress :",
                    color = color,
                    style = MaterialTheme.typography.bodySmall
                )
                Text("50%")
            }

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = 0.5f,
                trackColor = MaterialTheme.colorScheme.background

            )

            Button(
                modifier = Modifier.align(Alignment.End),
                onClick = onClick
            ) {
                Text(text = "Continue Exam")
            }

        }
    }
}

@Composable
internal expect fun ContinueCardPreview()

@Composable
fun StartCard() {
    Card() {
        Row(
            Modifier.padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {

            Text(
                modifier = Modifier.weight(1f),
                text = "Ready to challenge yourself with new test? Let go!"
            )

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Start exam")
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