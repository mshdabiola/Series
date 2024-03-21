package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.mshdabiola.ui.cbt.R

@Composable
actual fun getStringSubject(): String {
    return stringResource(id = R.string.modules_ui_cbt_subject)
}

@Composable
actual fun getStringType(): String {
    return stringResource(id = R.string.modules_ui_cbt_type)
}

@Composable
actual fun getExamPart(): Array<String> {
    return stringArrayResource(id = R.array.modules_ui_cbt_exam_part)
}

@Composable
actual fun getIconLayer2(): Painter {
    return painterResource(id = R.drawable.modules_ui_cbt_layer_2)
}

@Composable
actual fun getIconLayer1(): Painter {
    return painterResource(id = R.drawable.modules_ui_cbt_layer_1)
}

@Composable
actual fun getIconLayer3(): Painter {
    return painterResource(id = R.drawable.modules_ui_cbt_layer__1)
}

@Composable
actual fun getSection(): Array<String> {
    return stringArrayResource(id = R.array.modules_ui_cbt_sections)
}
