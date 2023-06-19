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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ContinueCard(
    onClick: () -> Unit={}
) {
    Card(colors = CardDefaults.cardColors(containerColor = Color.Yellow)) {
        Column(Modifier.padding(8.dp)) {
            Text(text = "You are soon closed to end, finish your quiz and find out your scores")
            Text(text = "Year :  2011")
            Text(text = "Question progress : 50%")
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth(), progress = 0.5f)

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
    Card(colors = CardDefaults.cardColors(containerColor = Color.Yellow)) {
        Row(Modifier.padding(8.dp)) {

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
                    .background(Color.Blue.copy(alpha = 0.4f))
                    .padding(16.dp)
            ) {
                Icon(
                    modifier = Modifier.size(64.dp),
                    painter = painter,
                    contentDescription = contentDesc,
                    tint = Color.Blue
                )
            }

            Text(text = title)
        }
    }
}

@Composable
internal expect fun OtherCardPreview()