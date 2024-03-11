package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.mshdabiola.ui.cbt.R

@Composable
actual fun getStringSubject(): String {
    return stringResource(id = R.string.subject)
}

@Composable
actual fun getStringType(): String {
    return stringResource(id = R.string.type)
}

@Composable
actual fun getExamPart(): Array<String> {
    return stringArrayResource(id = R.array.exam_part)
}

@Composable
actual fun getIconLayer2(): Painter {
    return painterResource(id = R.drawable.layer_2)
}

@Composable
actual fun getIconLayer1(): Painter {
    return painterResource(id = R.drawable.layer_1)
}

@Composable
actual fun getIconLayer3(): Painter {
    return painterResource(id = R.drawable.layer__1)
}

@Composable
actual fun getSection(): Array<String> {
    return stringArrayResource(id = R.array.sections)
}

