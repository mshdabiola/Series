package com.mshdabiola.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


val corrent = Color(0xFF3AC200)
val light_corrent = Color(0xFF1D6D00)
val light_oncorrent = Color(0xFFFFFFFF)
val light_correntContainer = Color(0xFF7BFF50)
val light_oncorrentContainer = Color(0xFF042100)
val dark_corrent = Color(0xFF5CE230)
val dark_oncorrent = Color(0xFF0B3900)
val dark_correntContainer = Color(0xFF145200)
val dark_oncorrentContainer = Color(0xFF7BFF50)

@Composable
fun MaterialTheme.correctContainer() =
    if (!isSystemInDarkTheme()) light_correntContainer else dark_correntContainer

@Composable
fun MaterialTheme.onCorrectContainer() =
    if (!isSystemInDarkTheme()) light_oncorrentContainer else dark_oncorrentContainer


@Composable
fun MaterialTheme.correct() =
    if (!isSystemInDarkTheme()) light_corrent else dark_corrent

@Composable
fun MaterialTheme.onCorrect() =
    if (!isSystemInDarkTheme()) light_oncorrent else dark_oncorrent

