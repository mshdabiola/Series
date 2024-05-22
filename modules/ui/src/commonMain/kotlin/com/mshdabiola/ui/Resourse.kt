package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.painter.Painter
import com.mshdabiola.series.ui.generated.resources.Res
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_exam_part
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_layer_1
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_layer_2
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_layer__1
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_sections
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_subject
import com.mshdabiola.series.ui.generated.resources.modules_ui_cbt_type
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringArrayResource
import org.jetbrains.compose.resources.stringResource


@Composable
 fun getStringSubject(): String{
     return stringResource(Res.string.modules_ui_cbt_subject)
 }

@Composable
 fun getStringType(): String {
 return stringResource(Res.string.modules_ui_cbt_type)
 }

@Composable
 fun getExamPart(): Array<String> {
 return stringArrayResource(Res.array.modules_ui_cbt_exam_part).toTypedArray()
}

@Composable
 fun getSection(): Array<String> {
 return stringArrayResource(Res.array.modules_ui_cbt_sections).toTypedArray()
}

@Composable
 fun getIconLayer2(): Painter {return painterResource(Res.drawable.modules_ui_cbt_layer_2)}


@Composable
 fun getIconLayer1(): Painter {return painterResource(Res.drawable.modules_ui_cbt_layer_1)}

@Composable
 fun getIconLayer3(): Painter {return painterResource(Res.drawable.modules_ui_cbt_layer__1)}
