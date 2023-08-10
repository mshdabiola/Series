package com.mshdabiola.ui

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.com.mshdabiola.ui.LargeDropdownMenu
import com.mshdabiola.ui.com.mshdabiola.ui.LargeDropdownMenuItem
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
internal actual fun StatCardPreview() {
    StartCard(
        exams = listOf(
            ExamUiState(
                id = 1,
                subjectID = 2,
                year = 2015,
                subject = "Jeanpaul",
                isObjOnly = false
            )
        ).toImmutableList(),
        isSubmit = false
    )
}

@Preview
@Composable
internal actual fun ContinueCardPreview() {
    ContinueCard(
        year = 2015,
        progress = 0.5f,
        time2 = 23,
        enabled = false
    )

}

@Preview
@Composable
internal actual fun OtherCardPreview() {
    OtherCard("OTherr", painter = painterResource(R.drawable.aaa))
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal actual fun YearExposed(
    modifier: Modifier,
    exams: ImmutableList<ExamUiState>,
    selectedOptionText: Int,
    label: String,
    onChange: (Int) -> Unit
) {
    LargeDropdownMenu(
        modifier=modifier,
        label = label,
        notSetLabel = "Select Exam Year",
        items = exams,
        selectedIndex = selectedOptionText,
        selectedItemToString = {it.year.toString()},
        onItemSelected = { index: Int, _:ExamUiState -> onChange(index) },
        drawItem = {item, selected, itemEnabled, onClick ->
            LargeDropdownMenuItem(
                text = item.year.toString(),
                selected = selected,
                enabled = itemEnabled,
                onClick = onClick,
            )
        }
    )
// We want to react on tap/press on TextField to show menu

}

@Composable
internal actual fun ExamType(
    modifier: Modifier,
    selectedOption: Int,
    onChange: (Int) -> Unit
) {
    val types= listOf("Objective & Theory","Theory","Objective")

    LargeDropdownMenu(
        modifier=modifier,
        label = "Exam Type",
        notSetLabel = "Select Exam Type",
        items = types,
        selectedIndex = selectedOption,
        selectedItemToString = {it},
        onItemSelected = { index: Int, _: String -> onChange(index) })

}