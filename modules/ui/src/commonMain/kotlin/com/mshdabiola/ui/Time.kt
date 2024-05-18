package com.mshdabiola.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

@Composable
fun TimeCounter(
    modifier: Modifier = Modifier,
    currentTime2: Long,
    total: Long,
    onTimeChanged: (Long) -> Unit = {},
) {
    val time = remember(total, currentTime2) {
        val remain = total - currentTime2

        String.format("%02d : %02d", remain.toMinute(), remain.toSecond())
    }
    val fraction = remember(currentTime2) {
        (currentTime2.toFloat() / total) * 360f
    }
    val ten = remember {
        (total * 0.8f).toInt()
    }
    val initColor = MaterialTheme.colorScheme.primary
    var color by remember {
        mutableStateOf(initColor)
    }

    LaunchedEffect(currentTime2) {
        withContext(Dispatchers.IO) {
            if (currentTime2 <= total) {
                delay(1000)
                onTimeChanged(currentTime2 + 1)
                if (currentTime2 > ten) {
                    color = if (currentTime2 % 2 == 0L) initColor else Color.Red
                }
            }
        }
    }

    Box(
        modifier = modifier
            .size(64.dp)
            .drawBehind {
                drawArc(
                    color = color,
                    startAngle = -90f,
                    sweepAngle = fraction,
                    useCenter = false,
                    style = Stroke(width = 4.dp.toPx(), cap = StrokeCap.Round),
                )
            },
        contentAlignment = Alignment.Center,
    ) {
        Text(time)
    }
}

fun Long.toMinute() = this.div(60)
fun Long.toSecond() = this.mod(60)
//
//@Composable
//internal expect fun TimeCounterPreview()
