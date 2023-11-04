package com.mshdabiola.series.screen

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@Composable
actual fun getStringSubject(): String {
    return "Waec Series"
}

@Composable
actual fun getStringType(): String {
    return "Physics"
}

@Composable
actual fun getExamPart(): Array<String> {
   return arrayOf("Obj & Theory", "Objective", "Theory")
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun getIconLayer2(): Painter {
    return painterResource("")
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun getIconLayer1(): Painter {
    return painterResource("")
}

@OptIn(ExperimentalResourceApi::class)
@Composable
actual fun getIconLayer3(): Painter {
    return painterResource("")
}

@Composable
actual fun getSection(): Array<String> {
   return arrayOf("Objective", "Theory")
}

actual fun Modifier.semantics(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit
) {
}

actual fun Modifier.semantics(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit
): Modifier {
    TODO("Not yet implemented")
}