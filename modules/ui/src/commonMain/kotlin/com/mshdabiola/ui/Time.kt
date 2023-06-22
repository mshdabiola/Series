package com.mshdabiola.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun TimeCounter(
    modifier: Modifier = Modifier,
    currentTime: Long,
    total: Long
) {

    val time = remember {
        val instant = Instant.fromEpochSeconds(currentTime)
        val time = instant.toLocalDateTime(TimeZone.UTC).time
        String.format("%02d : %02d", time.minute, time.second)
    }
    val fraction = remember {
        (currentTime.toFloat() / total) * 360f
    }
    val color = MaterialTheme.colorScheme.primary
    Box(
        modifier = modifier
            .size(64.dp)
            .drawBehind {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = fraction,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round)
                )
            },
        contentAlignment = Alignment.Center
    ) {
        Text(time)
    }
}

@Composable
internal expect fun TimeCounterPreview()