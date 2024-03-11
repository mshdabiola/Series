package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter

@Composable
expect fun getStringSubject(): String

@Composable
expect fun getStringType(): String

@Composable
expect fun getExamPart(): Array<String>


@Composable
expect fun getSection(): Array<String>

@Composable
expect fun getIconLayer2(): Painter

@Composable
expect fun getIconLayer1(): Painter

@Composable
expect fun getIconLayer3(): Painter
