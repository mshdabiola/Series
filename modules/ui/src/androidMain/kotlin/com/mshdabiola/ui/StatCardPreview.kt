package com.mshdabiola.ui

import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Preview
@Composable
internal actual fun StatCardPreview() {
    StartCard(
        exams = listOf(ExamUiState(
            id = 1,
            subjectID = 2,
            year = 2015,
            subject = "Jeanpaul"
        )).toImmutableList(),
        isSubmit = false
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
internal actual fun ContinueCardPreview() {
    ContinueCard(
        year = 2015,
        progress = 0.5f,
        isSubmit = false
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
    selectedOptionText : Int,
    supportText:String,
    onChange: (Int) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

// We want to react on tap/press on TextField to show menu
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        OutlinedTextField(
            modifier = modifier
                .menuAnchor(),
            readOnly = true,
            value = exams[selectedOptionText].year.toString(),
            onValueChange = {},
            label = { Text(supportText) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            exams.forEachIndexed { index, examUiState ->
                DropdownMenuItem(
                    text = { Text(examUiState.year.toString()) },
                    onClick = {
                        onChange(index)
                        expanded = false
                    }
                )
            }
        }
    }
}